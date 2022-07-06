package com.cgi.service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cgi.model.Admin;
import com.cgi.repository.AdminRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Value("${jwt.secret}")
	String key;
	
	@Autowired 
	private AdminRepository repository;

	@Override
	public Admin registerAdmin(Admin admin) {
		Optional<Admin> findAdmin = repository.findByUsername(admin.getUsername());
		try {
			if(findAdmin.isEmpty()) {
				SecureRandom secureRandom = new SecureRandom();
				byte[] salt = secureRandom.generateSeed(12);
			    admin.setHash(salt);
			    String hash = setHash(admin.getPassword(), salt);
			    if(hash != null) {
			    	admin.setPassword(hash);
				    return repository.save(admin);
			    }else {
			    	return null;
			    }
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
		// TODO Auto-generated method stub
	}

	@Override
	public Boolean verifyAdmin(String user, String password) {
		try {
			byte[] salt = getHash(user);
			String hash = setHash(password, salt);
			if(hash != null) {
				    Optional<Admin> verifyAdmin = repository.findByUsernameAndPassword(user, hash);
				    if(verifyAdmin.isPresent()) {
				    	return true;
				    }else {
				    	return false;
				    }
				}else {
					return false;
				}
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public int findUserId(String user, String password) {
		try {
			byte[] salt = getHash(user);
			String hash = setHash(password, salt);
			Optional<Admin> verifyAdmin = repository.findByUsernameAndPassword(user, hash);
			if(verifyAdmin.isPresent()) {
				return verifyAdmin.get().getId();
			}else {
				return -1;
			}
		}catch(Exception e) {
			return -1;
		}
	}

	@Override
	public byte[] getHash(String user) {
		Optional<Admin> admin = repository.findByUsername(user);
		if(admin.isPresent()) {
			return admin.get().getHash();
		}else {
			return null;
		}
	}
	
	public String setHash(String password, byte[] salt) {
		try {
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 10, 512);
		    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		    byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();
		    String base64Hash = Base64.getMimeEncoder().encodeToString(hash);
		    return base64Hash;
		}catch(Exception e) {
			return null;
		}
	    
	}
	
	public String generateJWTToken(String username) {
		return Jwts.builder().setSubject(username)
		.setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis()+3600000))
		.signWith(SignatureAlgorithm.HS256, key)
		.compact();
		
		
	}

}
