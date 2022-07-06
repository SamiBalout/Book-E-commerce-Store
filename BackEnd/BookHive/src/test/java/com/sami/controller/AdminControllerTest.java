package com.cgi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cgi.model.Admin;
import com.cgi.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class AdminControllerTest {
	
	private MockMvc mockMvc;
	
	private Admin admin, admin1, admin2; 
	
	@Mock
	AdminService service; 
	
	@InjectMocks 
	AdminController controller;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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
	
	@Test
	public void registerSuccess() throws Exception {
		when(service.registerAdmin(any())).thenReturn(admin);
		mockMvc.perform(post("/admin/register").contentType(MediaType.APPLICATION_JSON).content(asJsonString(admin))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void registerFailure() throws Exception {
		when(service.registerAdmin(any())).thenReturn(null);
		mockMvc.perform(post("/admin/register").contentType(MediaType.APPLICATION_JSON).content(asJsonString(admin))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
	}

	
	@Test
	public void loginSuccess() throws Exception {
		when(service.verifyAdmin(any(), any())).thenReturn(true);
		mockMvc.perform(post("/admin/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(admin))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void loginFailure() throws Exception {
		when(service.verifyAdmin(any(), any())).thenReturn(false);
		mockMvc.perform(post("/admin/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(admin))).andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void isAuthenticatedSuccess() throws Exception {
		mockMvc.perform(get("/admin/check/isAuthenticated").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	
	}
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
