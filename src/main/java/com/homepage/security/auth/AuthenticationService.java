package com.homepage.security.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homepage.security.token.JwtService;
import com.homepage.security.token.Token;
import com.homepage.security.token.TokenType;
import com.homepage.security.token.TokenRepository;
import com.homepage.user.entity.Role;
import com.homepage.user.entity.User;
import com.homepage.user.entity.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public boolean register(RegisterRequest request) {
		
		var user=User.builder()
				.uid(request.getUid())
				.pwd(passwordEncoder.encode(request.getPwd()))
				.nick(request.getNick())
				.email(request.getEmail())
				.emailReceive(request.getEmailReceive())
				.role(Role.USER)
				.build();
		userRepository.save(user);
		
		return true;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
					request.getUid(),
					request.getPwd()
			)
		);
		var uid=request.getUid();	
		var user=userRepository.findByUid(uid)
				.orElseThrow();
		var accessToken=jwtService.generateAccessToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(user, refreshToken);
		
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.nick(user.getNick())
				.build();
	}
	
	 private void saveUserToken(User user, String jwtToken) {
	     var token = Token.builder()
	         .user(user)
	         .token(jwtToken)
	         .tokenType(TokenType.BEARER)
	         .expired(false)
	         .revoked(false)
	         .build();
	     tokenRepository.save(token);
	}
	 
	private void revokeAllUserTokens(User user) {
	    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
	    if (validUserTokens.isEmpty())
	      return;
	    validUserTokens.forEach(token -> {
	      token.setExpired(true);
	      token.setRevoked(true);
	    });
	    tokenRepository.saveAll(validUserTokens);
	}
	
	public void refreshToken(
			HttpServletRequest request,
		    HttpServletResponse response	
	) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	    final String refreshToken;
	    final String uid;
	    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
	      return;
	    }
	    
	    refreshToken = authHeader.substring(7);
	    uid = jwtService.extractUid(refreshToken);
	    if(uid!=null) {
	    	var user= this.userRepository.findByUid(uid).orElseThrow();
	    	
	    	if(jwtService.isTokenValid(refreshToken, user)) {
	    		var accessToken=jwtService.generateAccessToken(user);
	    		revokeAllUserTokens(user);
//	            saveUserToken(user, refreshToken);
	            var authResponse = AuthenticationResponse.builder()
	                    .accessToken(accessToken)
	                    .refreshToken(refreshToken)
	                    .build();
	            
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.setContentType("application/json");
	            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
	            
	    	}
	    }
	}
	
}
