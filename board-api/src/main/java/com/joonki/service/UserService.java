package com.joonki.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.joonki.domain.User;
import com.joonki.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	public void signup(User user) {
		if(Strings.isNullOrEmpty(user.getUsername())) {
			throw new IllegalArgumentException("Emtpy username");
		}
		
		if(Strings.isNullOrEmpty(user.getPassword())) {
			throw new IllegalArgumentException("Emtpy password");
		}
		
		if(Strings.isNullOrEmpty(user.getName())) {
			throw new IllegalArgumentException("Emtpy name");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
}
