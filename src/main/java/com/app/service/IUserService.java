package com.app.service;

import java.util.List;

import com.app.dto.LoginRequest;
import com.app.pojos.User;

public interface IUserService {

	User registerOrEditUser(User user);
	
	User authenticateUser(LoginRequest request);

	User findByEmail(String email);

	User findById(Integer userId);

	List<User> getUsersByRole(String string);

	String deleteUserById(Integer uid);
}
