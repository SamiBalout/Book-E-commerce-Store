package com.cgi.service;

import com.cgi.model.Admin;

public interface AdminService {
	
	public Admin registerAdmin(Admin admin);
	
	public Boolean verifyAdmin(String user, String password);
	
	public int findUserId(String user, String password);
	
	public byte[] getHash(String user);
	
	public String generateJWTToken(String username);
	
}
