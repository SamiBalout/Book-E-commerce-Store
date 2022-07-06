package com.cgi.repoistory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cgi.model.Cart;
import com.cgi.model.User;
import com.cgi.repository.CartDetailsRepository;
import com.cgi.repository.CartRepository;
import com.cgi.repository.OrderDetailsRepository;
import com.cgi.repository.OrdersRepository;
import com.cgi.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CartRepositoryTest {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Autowired
	private CartDetailsRepository cartDetailsRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	private Cart cart1, cart2; 
	
	private User user1;
	
	private List<Cart> cartList;
	

	@BeforeEach
	public void setUp() {
		
		cartList = new ArrayList<Cart>();
		
		user1 = new User();
		user1.setEmail("William@william.com");
		user1.setPassword("william2");
		user1.setUsername("william2");
		user1.setFirstName("William");
		user1.setLastName("Mc");
		user1.setAddress("3000 Test Street Toronto Ontario");
		cart1 = new Cart();
		cart2 = new Cart();
		cart1.setUser(user1);
		cart2.setUser(user1);
		
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
		
		cartList.clear();
	}
	
	
	@Test
	void givenCartSaveTheCart() {
		Cart savedCart = cartRepository.saveAndFlush(cart1);
		assertEquals(cart1.getId(), savedCart.getId());
		assertEquals(user1, savedCart.getUser());
	}
	
	@Test
	void givenCartIdDeleteBook() {
		cartRepository.save(cart1);
		cartRepository.save(cart2);
		cartRepository.deleteById(cart2.getId());
		
		cartList = cartRepository.findAll();
		assertEquals(1, cartList.size());
	}

	@Test
	void findAllCarts() {
		cartRepository.save(cart1);
		cartRepository.save(cart2);
		cartList = cartRepository.findAll();
		assertEquals(2, cartList.size());
	}
	
	@Test
	void updateCartInfo() {
		cartRepository.save(cart1);
		Cart savedCart = cartRepository.findById(cart1.getId()).get();
		assertEquals(cart1.getId(), savedCart.getId());
		User savedUser = savedCart.getUser();
		savedUser.setFirstName("Update");
		savedCart.setUser(savedUser);
		cartRepository.save(savedCart);
		Cart updateCart = cartRepository.findById(cart1.getId()).get();
		assertEquals(cart1.getId(), updateCart.getId());
		assertEquals("Update", updateCart.getUser().getFirstName());
		
	}

}
