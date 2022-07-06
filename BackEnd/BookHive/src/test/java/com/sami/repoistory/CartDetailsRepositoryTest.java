package com.cgi.repoistory;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.cgi.model.Book;
import com.cgi.model.Cart;
import com.cgi.model.CartDetails;
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
public class CartDetailsRepositoryTest {
	
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
	
	@Autowired
	private BookRepository bookRepository;
	
	private CartDetails cartDetails1, cartDetails2, cartDetails3;
	
	private List<CartDetails> listCartDetails;
	
	private Book book1, book2, book3; 
	
	private Cart cart1;
	
	private User user1;
	
	@BeforeEach
	public void setUp() {
		listCartDetails = new ArrayList<CartDetails>();
		
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
		
		cart1 = new Cart();
		cart1.setUser(user1);
		
		user1 = new User();
		user1.setEmail("William@william.com");
		user1.setPassword("william2");
		user1.setUsername("william2");
		user1.setFirstName("William");
		user1.setLastName("Mc");
		user1.setAddress("3000 Test Street Toronto Ontario");
		
		cartDetails1 = new CartDetails();
		cartDetails2 = new CartDetails();
		cartDetails3 = new CartDetails();
		
		cartDetails1.setBook(book1);
		cartDetails1.setCart(cart1);
		cartDetails1.setQuantity(10);
		
		cartDetails2.setBook(book2);
		cartDetails2.setCart(cart1);
		cartDetails2.setQuantity(20);
		
		cartDetails3.setBook(book3);
		cartDetails3.setCart(cart1);
		cartDetails3.setQuantity(30);
		
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
		cartRepository.saveAndFlush(cart1);
		
		listCartDetails.clear();
	}
	
	@Test
	void givenCartDetailsSaveTheCartDetails() {
		cartDetailsRepository.save(cartDetails1);
		CartDetails savedDetails = cartDetailsRepository.findById(cartDetails1.getId()).get();
		assertEquals(cartDetails1.getId(), savedDetails.getId());
		assertEquals(book1, savedDetails.getBook());
		assertEquals(cart1, savedDetails.getCart());
	}
	
	@Test
	void givenCartDetailsDeleteCartDetails() {
		cartDetailsRepository.save(cartDetails1);
		cartDetailsRepository.save(cartDetails2);
		cartDetailsRepository.delete(cartDetails1);
		listCartDetails = cartDetailsRepository.findAll();
		
		assertEquals(1, listCartDetails.size());
	}

	@Test
	void findAllCartDetails() {
		cartDetailsRepository.save(cartDetails1);
		cartDetailsRepository.save(cartDetails2);
		listCartDetails = cartDetailsRepository.findAll();
		assertEquals(2, listCartDetails.size());
	}
	
	@Test
	void updateCartDetailsInfo() {
		cartDetailsRepository.save(cartDetails1);
		CartDetails savedDetails = cartDetailsRepository.findById(cartDetails1.getId()).get();
		assertEquals(cartDetails1.getId(), savedDetails.getId());
		savedDetails.setQuantity(100000);
		cartDetailsRepository.save(savedDetails);
		CartDetails updateDetails = cartDetailsRepository.findById(cartDetails1.getId()).get();
		assertEquals(cartDetails1.getId(), updateDetails.getId());
		assertEquals(100000, updateDetails.getQuantity());
		
	}
	
	

}
