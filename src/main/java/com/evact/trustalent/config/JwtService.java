package com.evact.trustalent.config;

import com.evact.trustalent.common.dto.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

	private final String SECRET_KEY;
	private final long EXPIRATION_TIME;

	public JwtService(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long expirationTime) {
		this.SECRET_KEY = secretKey;
		this.EXPIRATION_TIME = expirationTime;
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public TokenInfo generateToken(UserDetails userDetails) {
		Map<String, Object> extraClaims = new HashMap<>();

		String role = userDetails.getAuthorities().stream()
				.findFirst()
				.map(GrantedAuthority::getAuthority)
				.orElse(null);

		if (role != null) {
			extraClaims.put("role", role);
		}

		return generateToken(extraClaims, userDetails);
	}

	public TokenInfo generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

		Date expirationDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * EXPIRATION_TIME));
		String token =  Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(expirationDate)
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();

		return new TokenInfo(token, expirationDate);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
