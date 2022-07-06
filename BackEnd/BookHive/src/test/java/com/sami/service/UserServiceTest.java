package com.cgi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;

import com.cgi.dto.UserDto;
import com.cgi.model.Admin;
import com.cgi.model.User;
import com.cgi.repository.UserRepository;

public class UserServiceTest {
	
	
	private User user, user1, user2; 

	@Mock
	private UserRepository repository;
	
	@InjectMocks 
	private UserServiceImpl service; 
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
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
		user.setAddress("3000 Test Street Toronto Ontario");
		user.setPhone(1242141);
		user.setFirstName("William1");
		user.setLastName("McNiece1");
		user2 = new User();
		user2.setEmail("William@william.com");
		user2.setId(3);
		user2.setPassword("william3");
		user2.setUsername("william3");
		user.setAddress("1 Test Street Toronto Ontario");
		user.setPhone(121);
		user.setFirstName("William3");
		user.setLastName("McNiece3");
		
		
	}
	
	@Test
	@Rollback(true)
	public void getUserInfoSuccess() {
		when(repository.findById(any())).thenReturn(Optional.of(user));
		UserDto userDto = service.getUserInfo(user.getId());
		
		assertEquals(user.getFirstName(), userDto.getFirstName());
		assertEquals(user.getLastName(), userDto.getLastName());
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	@Rollback(true)
	public void getUserInfoFailure() {
		when(repository.findById(any())).thenReturn(Optional.empty());
		UserDto userDto = service.getUserInfo(user.getId());
		
		assertEquals(null, userDto);
		
		verify(repository, times(1)).findById(any());
	}
	
	@Test
	@Rollback(true)
	public void testSaveAdmin() {
		when(repository.findByUsername(any())).thenReturn(Optional.empty());
		when(repository.save(any())).thenReturn(user);
		assertEquals(user,service.registerUser(user));
		
		verify(repository, times(1)).save(any());
	}
	
	@Test
	@Rollback(true)
	public void testSaveAdminFail() {
		when(repository.findByUsername(any())).thenReturn(Optional.of(user));
		when(repository.save(any())).thenReturn(user);
		assertEquals(null,service.registerUser(user));
		
		verify(repository, times(0)).save(any());
	}
	
	
	@Test
	@Rollback(true)
	public void testGetUserByUsernameAndPassword() {
		when(repository.save(any())).thenReturn(user);
		User newUser = service.registerUser(user);
		when(repository.findByUsername(any())).thenReturn(Optional.of(newUser));
		when(repository.findByUsernameAndPassword(any(),any())).thenReturn(Optional.of(newUser));
		assertEquals(true,service.verifyUser(newUser.getUsername(),newUser.getPassword()));
		
		verify(repository, times(1)).findByUsernameAndPassword(any(), any());
	}
	
	@Test
	@Rollback(true)
	public void testGetUserFail() {
		when(repository.findByUsernameAndPassword(any(),any())).thenReturn(Optional.empty());
		assertEquals(false,service.verifyUser(user.getUsername(),user.getPassword()));
		
		verify(repository, times(0)).findByUsernameAndPassword(any(), any());
	}
	@Test
	@Rollback(true)
	public void testFindUserId() {
		when(repository.findByUsernameAndPassword(any(),any())).thenReturn(Optional.of(user));
		
		assertEquals(user.getId(), service.findUserId(user.getUsername(), user.getPassword()));
		verify(repository, times(1)).findByUsernameAndPassword(any(), any());
		
	}
	
	@Test
	@Rollback(true)
	public void testFindUserIdFail() {
		when(repository.findByUsernameAndPassword(any(),any())).thenReturn(Optional.empty());
		
		assertEquals(-1, service.findUserId(user.getUsername(), user.getPassword()));
		verify(repository, times(1)).findByUsernameAndPassword(any(), any());
		
	}
	
	@Test
	@Rollback(true)
	public void testGetHash() {
		when(repository.findByUsername(any())).thenReturn(Optional.of(user));
		
		assertEquals(user.getHash(), service.getHash(user.getUsername()));
		
		verify(repository, times(1)).findByUsername(any());
	}
	
	@Test
	@Rollback(true)
	public void testGetHashFail() {
		when(repository.findByUsername(any())).thenReturn(Optional.empty());
		
		assertEquals(null, service.getHash(user.getUsername()));
		
		verify(repository, times(1)).findByUsername(any());
	}

}
