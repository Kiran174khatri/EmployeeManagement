package com.example.employee_management.dto;

/**
 * Data Transfer Object (DTO) for sending the JWT token as a response after
 * successful authentication.
 */
public class AuthResponse {
	private String token;

	/**
	 * Constructs an AuthResponse with the provided JWT token.
	 * 
	 * @param token the JWT token
	 */
	public AuthResponse(String token) {
		this.token = token;
	}

	/**
	 * Returns the JWT token.
	 * 
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the JWT token.
	 * 
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}
