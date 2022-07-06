package com.cgi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.cgi.model.Admin;
import com.cgi.service.AdminService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("admin")
public class AdminController {
	
	@Autowired 
	private AdminService service; 
	
	@PostMapping("/register")
	public ResponseEntity<?> registerAdmin(@RequestBody Admin admin){
		Admin newAdmin = service.registerAdmin(admin); 
		if(newAdmin != null) {
			return new ResponseEntity<> (newAdmin,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<> ("Admin register failed", HttpStatus.CONFLICT);
		}	
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Admin admin){
		Map<String, String> token = new HashMap<>();
		boolean verifyUser = service.verifyAdmin(admin.getUsername(), admin.getPassword());
		if(verifyUser) {
			int findUserId = service.findUserId(admin.getUsername(), admin.getPassword());
			String stringId = String.valueOf(findUserId);
			String genToken = service.generateJWTToken(admin.getUsername());
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
	@GetMapping("/check/isAuthenticated")
	public ResponseEntity<?> isAuthenticated(){
		Map<String, String> check = new HashMap<>();
		check.put("access", "true");
		check.put("message", "Access Granted");
		return new ResponseEntity<> (check, HttpStatus.OK);
	}
	
	
	@GetMapping("/unauthorized")
	public ResponseEntity<?> errorMessage() {
		Map<String, String> check = new HashMap<>();
		check.put("access", "false");
		check.put("message", "Access Denied");
		return new ResponseEntity<> (check, HttpStatus.NOT_FOUND);
	}
	
	
	

}
