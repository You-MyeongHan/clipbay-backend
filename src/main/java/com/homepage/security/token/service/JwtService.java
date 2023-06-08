package com.homepage.security.token.service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.homepage.security.token.entity.RedisDao;
import com.homepage.security.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {
		
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value("${application.security.jwt.access-token.expiration}")
	private long accessExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;
	private final RedisDao redisDao;
	
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
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String generateRefreshToken(User user) {
		HashMap<String, Object> map = new HashMap<>() {{
			put("nick", user.getNick());
		}};
		String refreshToken=generateToken(map, user, refreshExpiration);
		redisDao.setValues(user.getId().toString(), refreshToken, Duration.ofMillis(accessExpiration));
		return refreshToken;
	}
	
	public String generateAccessToken(User user) {
		HashMap<String, Object> map = new HashMap<>() {{
			put("nick", user.getNick());
		}};
		return generateToken(map, user, accessExpiration); 
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