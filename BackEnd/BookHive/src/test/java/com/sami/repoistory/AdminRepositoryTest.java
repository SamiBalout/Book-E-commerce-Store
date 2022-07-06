package com.cgi.repoistory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cgi.model.Admin;
import com.cgi.repository.AdminRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AdminRepositoryTest {
	
	@Autowired 
	private AdminRepository repository;
	
	private Admin admin, admin1, admin2; 
	
	@BeforeEach
	public void setUp() {
		
		admin = new Admin();
		admin.setEmail("William@william.com");
		admin.setId(1);
		admin.setPassword("william1");
		admin.setUsername("william1");
		admin1 = new Admin();
		admin1.setEmail("William@william.com");
		admin1.setId(2);
		admin1.setPassword("william2");
		admin1.setUsername("william2");
		admin2 = new Admin();
		admin2.setEmail("William@william.com");
		admin2.setId(3);
		admin2.setPassword("william3");
		admin2.setUsername("william3");
		
		
	}
	
	@Test
	void givenAdminToSaveShouldReturnSavedAdmin() {
		repository.save(admin);
		Admin savedAdmin = repository.findById(1).get();
		assertEquals(1, savedAdmin.getId());
		assertEquals("william1", savedAdmin.getUsername());
	}
	
	@Test
	void givenUserNameFindAdmin() {
		repository.save(admin);
		Admin savedAdmin = repository.findByUsername(admin.getUsername()).get();
		assertEquals(1, savedAdmin.getId());
		assertEquals("william1", savedAdmin.getUsername());
	}
	
	@Test
	void givenUserNameFindAdminFail() {
		Optional<Admin> savedAdmin = repository.findByUsername("fail");
		assertEquals(Optional.empty(), savedAdmin);
	}
	
	@Test
	void givenUserNameAndPasswordFindAdmin() {
		repository.save(admin);
		Admin savedAdmin = repository.findByUsernameAndPassword(admin.getUsername(),admin.getPassword()).get();
		assertEquals(1, savedAdmin.getId());
		assertEquals("william1", savedAdmin.getUsername());
	}
	
	@Test
	void givenUserNameAndPasswordFindAdminFail() {
		Optional<Admin> savedAdmin = repository.findByUsernameAndPassword(admin.getUsername(),admin.getPassword());
		assertEquals(Optional.empty(), savedAdmin);
	}
	

}
