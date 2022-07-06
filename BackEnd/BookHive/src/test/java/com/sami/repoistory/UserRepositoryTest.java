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
import com.cgi.model.User;
import com.cgi.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
	

	@Autowired 
	private UserRepository repository;
	
	private User user, user1, user2; 
	
	@BeforeEach
	public void setUp() {
		
		user = new User();
		user.setEmail("William@william.com");
		user.setId(1);
		user.setPassword("william1");
		user.setUsername("william1");
		user.setAddress("252 Test Street Toronto Ontario");
		user.setPhone(1242151);
		user.setFirstName("William");
		user.setLastName("McNiece");
		user1 = new User();
		user1.setEmail("William@william.com");
		user1.setId(2);
		user1.setPassword("william2");
		user1.setUsername("william2");
		user1.setFirstName("William");
		user1.setLastName("Mc");
		user1.setAddress("3000 Test Street Toronto Ontario");
		user.setPhone(1242141);
		user.setFirstName("William1");
		user.setLastName("McNiece1");
		user2 = new User();
		user2.setEmail("William@william.com");
		user2.setId(3);
		user2.setPassword("william3");
		user2.setUsername("william3");
		user2.setAddress("1 Test Street Toronto Ontario");
		user2.setPhone(121);
		user2.setFirstName("William3");
		user2.setLastName("McNiece3");
		
		
	}
	
	@Test
	void givenUserToSaveShouldReturnSavedUser() {
		repository.save(user);
		User savedUser = repository.findById(1).get();
		assertEquals(1, savedUser.getId());
		assertEquals("william1", savedUser.getUsername());
	}
	@Test
	void giveUsernameFindUser() {
		repository.save(user);
		User savedUser = repository.findByUsername(user.getUsername()).get();
		assertEquals(1, savedUser.getId());
		assertEquals("william1", savedUser.getUsername());
	}
	
	@Test
	void givenUserNameFindUserFail() {
		Optional<User> savedUser = repository.findByUsername("fail");
		assertEquals(Optional.empty(), savedUser);
	}
	
	@Test
	void givenUserNameAndPasswordFindUser() {
		repository.save(user);
		User savedUser = repository.findByUsernameAndPassword(user.getUsername(),user.getPassword()).get();
		assertEquals(1, savedUser.getId());
		assertEquals("william1", savedUser.getUsername());
	}
	
	@Test
	void givenUserNameAndPasswordFindUserFail() {
		Optional<User> savedUser = repository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
		assertEquals(Optional.empty(), savedUser);
	}

}
