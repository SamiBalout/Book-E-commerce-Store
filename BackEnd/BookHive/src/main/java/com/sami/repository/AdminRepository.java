package com.cgi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgi.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	Optional<Admin> findByUsername(String username); //Check if user exists or not 
	
	Optional<Admin> findByUsernameAndPassword(String username, String password); //Login

}
