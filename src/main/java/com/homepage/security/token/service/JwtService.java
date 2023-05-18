package com.homepage.security.token.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.homepage.security.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
		
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value("${application.security.jwt.access-token.expiration}")
	private long accessExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;
	
	public String extractUid(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims= extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(
			Map<String, Object> extraClaims,
			User user,
			long expiration
	) {
		return  Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUid())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String generateRefreshToken(User user) {
		return generateToken(new HashMap<>(), user, refreshExpiration);
	}
	
	public String generateAccessToken(User user) {
		return generateToken(new HashMap<>(), user, accessExpiration); 
	}
	
	public boolean isTokenValid(String token, User user) {
		final String uid=extractUid(token);
		return (uid.equals(user.getUid())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSignInKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}