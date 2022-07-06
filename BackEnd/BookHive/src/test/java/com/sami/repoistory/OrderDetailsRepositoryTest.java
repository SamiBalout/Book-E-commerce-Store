package com.cgi.repoistory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cgi.model.Book;
import com.cgi.model.OrderDetails;
import com.cgi.model.Orders;
import com.cgi.model.User;
import com.cgi.repository.BookRepository;
import com.cgi.repository.CartDetailsRepository;
import com.cgi.repository.CartRepository;
import com.cgi.repository.OrderDetailsRepository;
import com.cgi.repository.OrdersRepository;
import com.cgi.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderDetailsRepositoryTest {
	
	@Autowired
	private OrderDetailsRepository orderDetailsRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartDetailsRepository cartDetailsRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	private OrderDetails orderDetails1, orderDetails2, orderDetails3;
	
	private List<OrderDetails> listOrderDetails;
	
	private Book book1, book2, book3; 
	
	private Orders order1;
	
	private User user1;
	
	@BeforeEach
	public void setUp() {
		
		
		user1 = new User();
		user1.setEmail("William@william.com");
		user1.setPassword("william2");
		user1.setUsername("william2");
		user1.setFirstName("William");
		user1.setLastName("Mc");
		user1.setAddress("3000 Test Street Toronto Ontario");
		
		book1 = new Book();
		book1.setActive(true);
		book1.setAuthor("Author");
		book1.setCategory("Category");
		book1.setDescription("Description");
		book1.setFormat("Format");
		book1.setGenre("Genre");
		book1.setImageUrl("image.com");
		book1.setName("Name");
		book1.setPrice(10.99);
		book1.setQuantity(10);
		book1.setType("Type1");
		
		book2 = new Book();
		book2.setActive(true);
		book2.setAuthor("Author");
		book2.setCategory("Category");
		book2.setDescription("Description");
		book2.setFormat("Format");
		book2.setGenre("Genre");
		book2.setImageUrl("image.com");
		book2.setName("Name");
		book2.setPrice(10.99);
		book2.setQuantity(10);
		book2.setType("Type2");
		
		book3 = new Book();
		book3.setActive(true);
		book3.setAuthor("Author");
		book3.setCategory("Category");
		book3.setDescription("Description");
		book3.setFormat("Format");
		book3.setGenre("Genre");
		book3.setImageUrl("image.com");
		book3.setName("Name");
		book3.setPrice(10.99);
		book3.setQuantity(10);
		book3.setType("Type3");
		
		order1 = new Orders();
		order1.setAddress("Test Address");
		order1.setDateTime(LocalDateTime.now());
		order1.setStatus("Pending");
		order1.setUser(user1);
		
		orderDetails1 = new OrderDetails();
		orderDetails1.setBook(book1);
		orderDetails1.setOrder(order1);
		orderDetails1.setQuantity(100);
		
		orderDetails2 = new OrderDetails();
		orderDetails2.setBook(book2);
		orderDetails2.setOrder(order1);
		orderDetails2.setQuantity(10);
		
		orderDetails3 = new OrderDetails();
		orderDetails3.setBook(book3);
		orderDetails3.setOrder(order1);
		orderDetails3.setQuantity(1);
		
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
		
		bookRepository.deleteAll();
		bookRepository.flush();
		
		userRepository.saveAndFlush(user1);
		bookRepository.saveAndFlush(book1);
		bookRepository.saveAndFlush(book2);
		bookRepository.saveAndFlush(book3);
		ordersRepository.saveAndFlush(order1);
	}
	
	@Test
	void givenOrderDetailsSaveTheOrderDetails() {
		orderDetailsRepository.save(orderDetails1);
		OrderDetails savedDetails = orderDetailsRepository.findById(orderDetails1.getId()).get();
		assertEquals(orderDetails1.getId(), savedDetails.getId());
		assertEquals(book1, savedDetails.getBook());
		assertEquals(order1, savedDetails.getOrder());
		assertEquals(100, savedDetails.getQuantity());
	}
	
	@Test
	void givenOrderDetailsDeleteOrderDetails() {
		orderDetailsRepository.save(orderDetails1);
		orderDetailsRepository.save(orderDetails2);
		orderDetailsRepository.delete(orderDetails1);
		listOrderDetails = orderDetailsRepository.findAll();
		
		assertEquals(1, listOrderDetails.size());
	}
	
	@Test
	void findAllOrderDetails() {
		orderDetailsRepository.save(orderDetails1);
		orderDetailsRepository.save(orderDetails2);
		listOrderDetails = orderDetailsRepository.findAll();
		assertEquals(2, listOrderDetails.size());
	}
	
	@Test
	void updateCartDetailsInfo() {
		orderDetailsRepository.save(orderDetails1);
		OrderDetails savedDetails = orderDetailsRepository.findById(orderDetails1.getId()).get();
		assertEquals(orderDetails1.getId(), savedDetails.getId());
		savedDetails.setQuantity(100000);
		orderDetailsRepository.save(savedDetails);
		OrderDetails updateDetails = orderDetailsRepository.findById(orderDetails1.getId()).get();
		assertEquals(orderDetails1.getId(), updateDetails.getId());
		assertEquals(100000, updateDetails.getQuantity());
		
	}
	

}
