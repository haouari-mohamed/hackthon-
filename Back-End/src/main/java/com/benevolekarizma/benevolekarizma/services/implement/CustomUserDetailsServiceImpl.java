package com.benevolekarizma.benevolekarizma.services.implement;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.benevolekarizma.benevolekarizma.models.User;
import com.benevolekarizma.benevolekarizma.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for custom user details.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with this username: " + username));

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				true,
				true, // accountNonExpired
				true, // credentialsNonExpired
				true, // accountNonLocked
				getAuthorities(user));

	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		return Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString()));
	}
}
