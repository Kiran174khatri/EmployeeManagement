package com.example.employee_management.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JwtTokenProvider handles the creation, validation, and parsing of JWT tokens.
 * It uses a secret key for signing tokens and supports extracting claims like
 * username and role.
 */
@Component
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;

	/**
	 * Returns the signing key based on the secret.
	 */
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	/**
	 * Generates a JWT token containing the username and role as claims.
	 *
	 * @param username the username to set as subject
	 * @param role     the role to embed as a custom claim
	 * @return signed JWT token as String
	 */
	public String generateToken(String username, String role) {
		return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	/**
	 * Validates a given JWT token by checking its signature and expiration.
	 *
	 * @param token the JWT token
	 * @return true if token is valid, false otherwise
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	/**
	 * Extracts the username (subject) from the JWT token.
	 *
	 * @param token the JWT token
	 * @return the username (subject)
	 */

	public String getUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Extracts the user role from the JWT token claims.
	 *
	 * @param token the JWT token
	 * @return the role as a String
	 */
	public String getRole(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().get("role",
				String.class);
	}
}
