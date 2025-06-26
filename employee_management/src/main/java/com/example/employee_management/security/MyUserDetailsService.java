package com.example.employee_management.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.employee_management.entity.User;
import com.example.employee_management.repository.UserRepository;

/**
 * MyUserDetailsService is a custom implementation of Spring Security's
 * UserDetailsService. It is responsible for loading user-specific data from the
 * database during authentication.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Loads the user from the database by username. Converts the application's User
	 * entity into a Spring Security-compatible UserDetails object.
	 *
	 * @param username the username entered during login
	 * @return UserDetails object containing username, password, and authorities
	 * @throws UsernameNotFoundException if the user is not found in the database
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user == null)
			throw new UsernameNotFoundException("User not found");
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));

	}

}