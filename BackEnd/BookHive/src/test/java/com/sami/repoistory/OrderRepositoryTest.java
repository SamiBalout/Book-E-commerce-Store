package com.cgi.repoistory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cgi.model.Orders;
import com.cgi.model.User;
import com.cgi.repository.CartDetailsRepository;
import com.cgi.repository.CartRepository;
import com.cgi.repository.OrderDetailsRepository;
import com.cgi.repository.OrdersRepository;
import com.cgi.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderRepositoryTest {
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Autowired
	private CartDetailsRepository cartDetailsRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	private Orders order1, order2, order3; 
	
	private List<Orders> orderList; 
	
	private User user1;
	
	@BeforeEach
	public void setUp() {
		
		user1 = new User();
		user1.setEmail("William@william.com");
		user1.setPassword("william2");
		user1.setUsername("william2");
		user1.setFirstName("William");
		user1.setLastName("Mc");
		
		orderList = new ArrayList<Orders>();
		
		order1 = new Orders();
		order1.setAddress("Test Address");
		order1.setDateTime(LocalDateTime.now());
		order1.setStatus("Pending");
		order1.setUser(user1);
		
		order2 = new Orders();
		order2.setAddress("Test Address2");
		order2.setDateTime(LocalDateTime.now());
		order2.setStatus("Pending");
		order2.setUser(user1);
		
		order3 = new Orders();
		order3.setAddress("Test Address3");
		order3.setDateTime(LocalDateTime.now());
		order3.setStatus("Pending");
		order3.setUser(user1);
		
		orderDetailsRepository.deleteAll();
		orderDetailsRepository.flush();
		
		ordersRepository.deleteAll();
		ordersRepository.flush();
		
		cartDetailsRepository.deleteAll();
		cartDetailsRepository.flush();
		
		cartRepository.deleteAll();
		cartRepository.flush();
		
		userRepository.deleteAll();
		userRepository.flush();
		
		userRepository.saveAndFlush(user1);
	}
	
	@Test
	void givenOrderSaveTheOrder() {
		ordersRepository.save(order1);
		Orders savedOrder = ordersRepository.findById(order1.getId()).get();
		assertEquals(order1.getId(), savedOrder.getId());
		assertEquals(user1, savedOrder.getUser());
	}
	
	@Test
	void givenOrderIdDeleteBook() {
		ordersRepository.save(order1);
		ordersRepository.save(order2);
		ordersRepository.deleteById(order2.getId());
		
		orderList = ordersRepository.findAll();
		assertEquals(1, orderList.size());	
	}
	
	@Test
	void findAllOrders() {
		ordersRepository.save(order1);
		ordersRepository.save(order2);
		orderList = ordersRepository.findAll();
		assertEquals(2, orderList.size());	
	}
	
	@Test
	void updateOrderInfo() {
		ordersRepository.save(order1);
		Orders savedOrder = ordersRepository.findById(order1.getId()).get();
		assertEquals(order1.getId(), savedOrder.getId());
		User savedUser = savedOrder.getUser();
		savedUser.setFirstName("Update");
		savedOrder.setUser(savedUser);
		savedOrder.setAddress("Address");
		ordersRepository.save(savedOrder);
		Orders updateOrder = ordersRepository.findById(order1.getId()).get();
		assertEquals(order1.getId(), updateOrder.getId());
		assertEquals("Address", updateOrder.getAddress());
		assertEquals("Update", updateOrder.getUser().getFirstName());
		
	}
	
	

}
