package com.cgi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cgi.dto.UserDto;
import com.cgi.model.User;
import com.cgi.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class UserControllerTest {
	

	private MockMvc mockMvc;
	
	private User user, user1, user2; 
	
	private UserDto userDto; 
	
	@Mock
	private UserServiceImpl service;
	
	@InjectMocks 
	private UserController controller; 
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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
		
		userDto = new UserDto(); 
		userDto.setAddress(user.getAddress());
		userDto.setEmail(user.getEmail());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setPhone(user.getPhone());
		userDto.setUsername(user.getUsername());
		
	}
	
	@Test
	public void getUserInfoSuccess() throws Exception {
		when(service.getUserInfo(1)).thenReturn(userDto);
		mockMvc.perform(get("/user/getUser/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void getUserInfoFailure() throws Exception {
		when(service.getUserInfo(1)).thenReturn(null);
		mockMvc.perform(get("/user/getUser/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void registerSuccess() throws Exception {
		when(service.registerUser(any())).thenReturn(user);
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void registerFailure() throws Exception {
		when(service.registerUser(user)).thenReturn(null);
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
	}

	
	@Test
	public void loginSuccess() throws Exception {
		when(service.verifyUser(any(), any())).thenReturn(true);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user))).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void loginFailure() throws Exception {
		when(service.verifyUser(any(), any())).thenReturn(false);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user))).andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void isAuthenticatedSuccess() throws Exception {
		mockMvc.perform(post("/user/check/isAuthenticated").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	
	}
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

}
