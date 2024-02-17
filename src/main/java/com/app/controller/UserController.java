package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.UserNotFoundException;
import com.app.dto.LoginRequest;
import com.app.dto.LoginResponse;
import com.app.dto.ResponseDto;
import com.app.pojos.User;
import com.app.service.IUserService;
import com.app.util.JwtUtil;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	
	// Injecting dependencies by type
	
	@Autowired
	private JwtUtil jwtUtil;		// Dependency for jwtToken
	
	@Autowired
	private IUserService userService;
	
	@Autowired //injecting authentication manager - created in the filter
	private AuthenticationManager authenticationManager;
	
	public UserController() {
		System.out.println("---------- CTOR: "+ getClass().getName() +" -----------");
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		System.out.println("in create new User"+user);
		// Sending a generic response which consists of status and data
		return new ResponseEntity<>(new ResponseDto<User>("success", userService.registerOrEditUser(user)), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request){
		System.out.println("in authenticate user"+request);
		try {
			// authenticating the user
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			System.out.println("\n------ Authenticated userDetails:" + authenticate + " -------\n");
			
		} catch (Exception e) {
			throw new UserNotFoundException("Invalid username or password");
		}
		User user =  userService.findByEmail(request.getEmail());	
		return new ResponseEntity<>(new LoginResponse("success", user, jwtUtil.generateToken(user.getId())), HttpStatus.OK );
	}
	
	@PutMapping("/edit/{uid}")
	public ResponseEntity<?> editUser(@RequestBody User user, @PathVariable int uid ){
		// populate the user object with existing id of the user
		user.setId(uid);
		return new ResponseEntity<>(new ResponseDto<User>("success", userService.registerOrEditUser(user)), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/customers")
	public ResponseEntity<?> getAllCustomers() {
		return new ResponseEntity<>(new ResponseDto<List<User>>("success", userService.getUsersByRole("CUSTOMER")), HttpStatus.OK);
	}
	
	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployees() {
		return new ResponseEntity<>(new ResponseDto<List<User>>("success", userService.getUsersByRole("EMPLOYEE")), HttpStatus.OK);
	}
	
	@GetMapping("/delivery_persons")
	public ResponseEntity<?> getAllDeliverers() {
		return new ResponseEntity<>(new ResponseDto<List<User>>("success", userService.getUsersByRole("DELIVERY_PERSON")), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{uid}")
	public ResponseEntity<?> deleteUserById(@PathVariable Integer uid){
		return new ResponseEntity<>(new ResponseDto<String>("success", userService.deleteUserById(uid)), HttpStatus.OK);
	}
	
	@GetMapping("/health")
	public ResponseEntity<?> checkHealth() {
		return new ResponseEntity<>("healthy",HttpStatus.OK);
	}
}