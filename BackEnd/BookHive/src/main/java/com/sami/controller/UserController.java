package com.cgi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.dto.UserDto;
import com.cgi.model.User;
import com.cgi.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("user")
public class UserController {
	
	@Autowired 
	private UserService service; 
	
	@PostMapping("/register")
	public ResponseEntity<?> registerAdmin(@RequestBody User user){
		User newUser = service.registerUser(user); 
		if(newUser != null) {
			return new ResponseEntity<> (newUser,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<> ("User register failed", HttpStatus.CONFLICT);
		}	
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user){
		Map<String, String> token = new HashMap<>();
		boolean verifyUser = service.verifyUser(user.getUsername(), user.getPassword());
		if(verifyUser) {
			int findUserId = service.findUserId(user.getUsername(), user.getPassword());
			String stringId = String.valueOf(findUserId);
			String genToken = service.generateJWTToken(user.getUsername());
			token.put("id", stringId);
			token.put("token", genToken);
			token.put("message", "Valid User Credentials");
			return new ResponseEntity<> (token, HttpStatus.OK);
		}else {
			token.put("token", null);
			token.put("message", "Invalid User Credentials");
			return new ResponseEntity<> (token, HttpStatus.FORBIDDEN);
		}
		
		
	}
	@GetMapping("/getUser/{id}")
	public ResponseEntity<?> getUserInfo(@PathVariable("id") int id ){
		UserDto userDto = service.getUserInfo(id);
		if(userDto != null) {
			return new ResponseEntity<> (userDto,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<> ("User not found", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/check/isAuthenticated")
	public ResponseEntity<?> isAuthenticated(){
		Map<String, String> check = new HashMap<>();
		check.put("access", "true");
		check.put("message", "Access Granted");
		return new ResponseEntity<> (check, HttpStatus.OK);
	}

}
