package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmailAndPassword(String email, String password);
	
	User findByEmail(String email);
}
