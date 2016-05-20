package com.joonki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joonki.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
