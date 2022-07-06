package com.cgi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.any;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;

import com.cgi.model.Admin;
import com.cgi.repository.AdminRepository;

public class AdminServiceTest {
	
	
	@Mock
	private AdminRepository repository;
	
	@InjectMocks 
	private AdminServiceImpl service; 
	
	private Admin admin, admin1, admin2; 
	
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		admin = new Admin();
		admin.setEmail("William@william.com");
		admin.setId(1);
		admin.setPassword("william1");
		admin.setUsername("william1");
		admin1 = new Admin();
		admin1.setEmail("William@william.com");
		admin1.setId(2);
		admin1.setPassword("william1");
		admin1.setUsername("william1");
		admin2 = new Admin();
		admin2.setEmail("William@william.com");
		admin2.setId(3);
		admin2.setPassword("william1");
		admin2.setUsername("william1");
		
		
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		admin = null; 
	}
	
	@Test
	@Rollback(true)
	public void testSaveAdmin() {
		when(repository.findByUsername(any())).thenReturn(Optional.empty());
		when(repository.save(any())).thenReturn(admin);
		assertEquals(admin,service.registerAdmin(admin));
		
		verify(repository, times(1)).save(any());
	}
	
	@Test
	@Rollback(true)
	public void testSaveAdminFail() {
		when(repository.findByUsername(any())).thenReturn(Optional.of(admin));
		when(repository.save(any())).thenReturn(admin);
		assertEquals(null,service.registerAdmin(admin));
		
		verify(repository, times(0)).save(any());
	}
	
	
	@Test
	@Rollback(true)
	public void testGetUserByUsernameAndPassword() {
		when(repository.save(any())).thenReturn(admin);
		Admin newAdmin = service.registerAdmin(admin);
		when(repository.findByUsername(any())).thenReturn(Optional.of(newAdmin));
		when(repository.findByUsernameAndPassword(any(),any())).thenReturn(Optional.of(newAdmin));
		assertEquals(true,service.verifyAdmin(newAdmin.getUsername(),newAdmin.getPassword()));
		
		verify(repository, times(1)).findByUsernameAndPassword(any(), any());
	}
	
	@Test
	@Rollback(true)
	public void testGetUserFail() {
		when(repository.findByUsernameAndPassword(any(),any())).thenReturn(Optional.empty());
		assertEquals(false,service.verifyAdmin(admin.getUsername(),admin.getPassword()));
		
		verify(repository, times(0)).findByUsernameAndPassword(any(), any());
	}
	@Test
	@Rollback(true)
	public void testFindUserId() {
		when(repository.findByUsernameAndPassword(any(),any())).thenReturn(Optional.of(admin));
		
		assertEquals(admin.getId(), service.findUserId(admin.getUsername(), admin.getPassword()));
		verify(repository, times(1)).findByUsernameAndPassword(any(), any());
		
	}
	
	@Test
	@Rollback(true)
	public void testFindUserIdFail() {
		when(repository.findByUsernameAndPassword(any(),any())).thenReturn(Optional.empty());
		
		assertEquals(-1, service.findUserId(admin.getUsername(), admin.getPassword()));
		verify(repository, times(1)).findByUsernameAndPassword(any(), any());
		
	}
	
	@Test
	@Rollback(true)
	public void testGetHash() {
		when(repository.findByUsername(any())).thenReturn(Optional.of(admin));
		
		assertEquals(admin.getHash(), service.getHash(admin.getUsername()));
		
		verify(repository, times(1)).findByUsername(any());
	}
	
	@Test
	@Rollback(true)
	public void testGetHashFail() {
		when(repository.findByUsername(any())).thenReturn(Optional.empty());
		
		assertEquals(null, service.getHash(admin.getUsername()));
		
		verify(repository, times(1)).findByUsername(any());
	}

}
