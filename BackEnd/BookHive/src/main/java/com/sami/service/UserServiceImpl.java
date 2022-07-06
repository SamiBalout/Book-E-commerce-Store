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

import com.cgi.dto.UserDto;
import com.cgi.model.User;
import com.cgi.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService{
	
	@Value("${jwt.secret}")
	String key;
	
	@Autowired 
	private UserRepository repository;

	@Override
	public User registerUser(User user) {
		Optional<User> findAdmin = repository.findByUsername(user.getUsername());
		try {
			if(findAdmin.isEmpty()) {
				SecureRandom secureRandom = new SecureRandom();
				byte[] salt = secureRandom.generateSeed(12);
				user.setHash(salt);
			    String hash = setHash(user.getPassword(), salt);
			    if(hash != null) {
			    	user.setPassword(hash);
				    return repository.save(user);
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
	public Boolean verifyUser(String user, String password) {
		try {
			byte[] salt = getHash(user);
			String hash = setHash(password, salt);
			if(hash != null) {
				    Optional<User> verifyUser = repository.findByUsernameAndPassword(user, hash);
				    if(verifyUser.isPresent()) {
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
			Optional<User> verifyUser = repository.findByUsernameAndPassword(user, hash);
			if(verifyUser.isPresent()) {
				return verifyUser.get().getId();
			}else {
				return -1;
			}
		}catch(Exception e) {
			return -1;
		}
	}

	@Override
	public byte[] getHash(String user) {
		Optional<User> mainUser = repository.findByUsername(user);
		if(mainUser.isPresent()) {
			return mainUser.get().getHash();
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
		.setExpiration(new Date(System.currentTimeMillis()+100000))
		.signWith(SignatureAlgorithm.HS256, key)
		.compact();
		
		
	}

	@Override
	public UserDto getUserInfo(int id) {
		try {
		Optional<User> user = repository.findById(id);
		if(user.isPresent()) {
			 UserDto userDto = new UserDto(); 
			 userDto.setAddress(user.get().getAddress());
			 userDto.setEmail(user.get().getEmail());
			 userDto.setFirstName(user.get().getFirstName());
			 userDto.setLastName(user.get().getLastName());
			 userDto.setPhone(user.get().getPhone());
			 userDto.setUsername(user.get().getUsername());
			 return userDto; 
		}else {
			return null; 
		}
		}catch(Exception e) {
			return null;
		}
	}

}
