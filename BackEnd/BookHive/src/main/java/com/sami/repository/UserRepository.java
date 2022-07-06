package com.cgi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username); //Check if user exists or not 
	
	Optional<User> findByUsernameAndPassword(String username, String password); //Login

}
