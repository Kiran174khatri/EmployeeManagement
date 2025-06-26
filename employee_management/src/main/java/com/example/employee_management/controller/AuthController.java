package com.example.employee_management.controller;

import com.example.employee_management.dto.AuthRequest;
import com.example.employee_management.dto.AuthResponse;
import com.example.employee_management.entity.User;
import com.example.employee_management.repository.UserRepository;
import com.example.employee_management.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Authenticates the user and returns a JWT token if credentials are valid.
 * 
 * @param request Contains the username and password.
 * @return JWT token wrapped in AuthResponse on successful authentication.
 * @throws UsernameNotFoundException if the user does not exist in the database.
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	/**
	 * Authenticates the user using the provided username and password. If
	 * authentication is successful, generates and returns a JWT token.
	 *
	 * @param request the authentication request containing username and password
	 * @return ResponseEntity containing the JWT token if credentials are valid
	 * @throws UsernameNotFoundException if the user is not found in the database
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		User user = userRepository.findByUsername(request.getUsername());
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		String jwtToken = jwtTokenProvider.generateToken(user.getUsername(), user.getRole().name());

		return ResponseEntity.ok(new AuthResponse(jwtToken));
	}
}
