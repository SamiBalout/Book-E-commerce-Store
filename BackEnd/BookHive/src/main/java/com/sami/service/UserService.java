package com.cgi.service;

import com.cgi.dto.UserDto;
import com.cgi.model.User;

public interface UserService  {

	public User registerUser(User user);
	
	public Boolean verifyUser(String user, String password);
	
	public int findUserId(String user, String password);
	
	public byte[] getHash(String user);
	
	public String generateJWTToken(String username);
	
	public UserDto getUserInfo(int id);
	
}
