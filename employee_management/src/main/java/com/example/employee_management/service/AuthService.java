package com.example.employee_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.employee_management.dto.AuthRequest;
import com.example.employee_management.dto.AuthResponse;
import com.example.employee_management.entity.User;
import com.example.employee_management.repository.UserRepository;
import com.example.employee_management.security.JwtTokenProvider;

/**
 * AuthService handles user authentication logic. It verifies user credentials
 * using Spring Security's AuthenticationManager, and generates a JWT token if
 * authentication is successful.
 */
@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	/**
	 * Authenticates the user and returns a JWT token if valid.
	 *
	 * @param request contains username and password
	 * @return AuthResponse with JWT token
	 */
	public AuthResponse authenticate(AuthRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		User user = userRepository.findByUsername(request.getUsername());
		String token = tokenProvider.generateToken(user.getUsername(), user.getRole().name());
		return new AuthResponse(token);
	}

}
