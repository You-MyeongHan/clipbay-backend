package com.homepage.security.auth.service;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homepage.security.auth.entity.AuthenticationRequest;
import com.homepage.security.auth.entity.AuthenticationResponse;
import com.homepage.security.auth.entity.RegisterRequest;
import com.homepage.security.token.entity.Token;
import com.homepage.security.token.entity.TokenType;
import com.homepage.security.token.repository.TokenRepository;
import com.homepage.security.token.service.JwtService;
import com.homepage.security.user.entity.Role;
import com.homepage.security.user.entity.User;
import com.homepage.security.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements LogoutHandler{
	
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
		var user=userRepository.findByUid(request.getUid())
				.orElseThrow();
		var accessToken=jwtService.generateAccessToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		
		return AuthenticationResponse.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}
	
	@Override
	public void logout(
			HttpServletRequest request,
		    HttpServletResponse response,
		    Authentication authentication
	){
		final String authHeader = request.getHeader("Authorization");
	    final String jwt;
	    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
	      return;
	    }
	    jwt = authHeader.substring(7);
	    var storedToken = tokenRepository.findByToken(jwt)
	        .orElse(null);
	    if (storedToken != null) {
	      storedToken.setExpired(true);
	      storedToken.setRevoked(true);
	      tokenRepository.save(storedToken);
	      SecurityContextHolder.clearContext();
	    }
	}
	
	public boolean existsByUid(String uid){
		return !userRepository.existsByUid(uid);
	}
	
	public boolean existsByEmail(String email){
		return !userRepository.existsByEmail(email);
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
