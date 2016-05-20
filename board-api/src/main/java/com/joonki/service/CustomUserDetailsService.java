package com.joonki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joonki.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.joonki.domain.User user = userRepository.findByUsername(username);
		
		return new User(
			user.getUsername(),
			user.getPassword(),
			user.isEnabled(),
			user.isAccountNonExpired(),
			user.isCredentialsNonExpired(),
			user.isAccountNonLocked(),
			user.getAuthorities());
	}
}
