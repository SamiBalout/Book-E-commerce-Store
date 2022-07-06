package com.cgi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;

import com.cgi.model.Book;
import com.cgi.model.Cart;
import com.cgi.model.CartDetails;
import com.cgi.model.User;
import com.cgi.repository.BookRepository;
import com.cgi.repository.CartDetailsRepository;
import com.cgi.repository.CartRepository;
import com.cgi.repository.UserRepository;


public class CartServiceTest {

	@Mock
	private CartDetailsRepository repository;

	@Mock
	private CartRepository cartRepository;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private CartServiceImpl service;

	private Cart cart1, cart2, cart3;

	private Book book1, book2;

	private User user1;

	private List<Cart> cartList;

	private List<CartDetails> cartDetailsList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		cartList = new ArrayList<Cart>();
		cartDetailsList = new ArrayList<CartDetails>();
		user1 = new User();
		user1.setEmail("William@william.com");
		user1.setId(2);
		user1.setPassword("william2");
		user1.setUsername("william2");
		user1.setFirstName("William");
		user1.setLastName("Mc");
		user1.setAddress("3000 Test Street Toronto Ontario");
		cart1 = new Cart();
		cart2 = new Cart();
		cart3 = new Cart();
		cart1.setId(1);
		cart1.setUser(user1);
		cart2.setId(2);
		cart2.setUser(user1);
		cart3.setId(3);
		cart3.setUser(user1);

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
		book1.setType("Type");
		book1.setId(1);

		book2 = new Book();
		book2.setActive(true);
		book2.setAuthor("Author1");
		book2.setCategory("Category1");
		book2.setDescription("Description1");
		book2.setFormat("Format1");
		book2.setGenre("Genre1");
		book2.setImageUrl("image.com1");
		book2.setName("Name1");
		book2.setPrice(1.99);
		book2.setQuantity(100);
		book2.setType("Type1");
		book2.setId(2);

	}

	@AfterEach
	public void tearDown() throws Exception {
		cart1 = null;
		cartList = null;
	}

	@Test
	@Rollback(true)
	public void deleteCartDetailsByCartId() {
		CartDetails cartDetails = new CartDetails();
		cartDetails.setBook(book1);
		cartDetails.setCart(cart1);
		cartDetails.setQuantity(10);
		cartDetails.setId(1);
		cartDetailsList.add(cartDetails);
		when(repository.findAll().stream().filter((details) -> details.getCart().getId() == cart1.getId())
				.collect(Collectors.toList())).thenReturn(cartDetailsList);
		service.deleteCartDetailsByCartId(1);
		verify(repository, times(1)).delete(any());
	}

	@Test
	@Rollback(true)
	public void getCartIdByCustomerId() {
		cartList.add(cart1);
		when(cartRepository.findAll().stream().filter((cart) -> cart.getUser().getId() == user1.getId())
				.collect(Collectors.toList())).thenReturn(cartList);
		assertEquals(cart1.getId(), service.getCartIdByCustomerId(user1.getId()));
	}

	@Test
	@Rollback(true)
	public void getAllCartDetailsByCartId() {
		CartDetails cartDetails = new CartDetails();
		cartDetails.setBook(book1);
		cartDetails.setCart(cart1);
		cartDetails.setQuantity(10);
		cartDetailsList.add(cartDetails);
		
		when(repository.findAll().stream().filter((details) -> details.getCart().getId() == cart1.getId())
				.collect(Collectors.toList())).thenReturn(cartDetailsList);
		assertEquals(cartDetailsList, service.getAllCartDetailsByCartId(cart1.getId()));
	}

	@Test
	@Rollback(true)
	public void updateBookQuantityByCartDetailsId() {
		CartDetails cartDetails = new CartDetails();
		cartDetails.setBook(book1);
		cartDetails.setCart(cart1);
		cartDetails.setQuantity(10);
		cartDetailsList.add(cartDetails);
		when(repository.findAll().stream().filter(
				(cartDetail) -> cartDetail.getBook().getId() == book1.getId() && cartDetail.getCart().getId() == cart1.getId())
				.collect(Collectors.toList())).thenReturn(cartDetailsList);
		when(repository.save(cartDetails)).thenReturn(cartDetails);
		CartDetails updatedCartDetail = service.updateBookQuantityByCartDetailsId(cart1.getId(), book1.getId(), 20);
		assertEquals(20, updatedCartDetail.getQuantity());
	}

	@Test
	@Rollback(true)
	public void addNewCart() {
		when(repository.findAll().stream().filter((cart) -> cart.getCart().getUser().getId() == user1.getId())
				.collect(Collectors.toList())).thenReturn(cartDetailsList);
		when(userRepository.findById(any())).thenReturn(Optional.of(user1));
		when(cartRepository.saveAndFlush(any())).thenReturn(cart1);
		assertEquals(cart1, service.addNewCart(user1.getId()));
	}

	@Test
	@Rollback(true)
	public void addNewBookToCart() {
	
		CartDetails cartDetails2 = new CartDetails();
		cartDetails2.setBook(book2);
		cartDetails2.setCart(cart2);
		cartDetails2.setQuantity(10);
		
		when(repository.findAll().stream().filter(
				(cartDetail) -> cartDetail.getBook().getId() == book2.getId() && cartDetail.getCart().getId() == cart2.getId())
				.collect(Collectors.toList())).thenReturn(cartDetailsList);
		when(bookRepository.findById(book2.getId())).thenReturn(Optional.of(book2));
		when(cartRepository.findById(cart2.getId())).thenReturn(Optional.of(cart2));
		when(repository.saveAndFlush(any())).thenReturn(cartDetails2);
		assertEquals(cartDetails2, service.addNewBookToCart(book2.getId(), cart2.getId(), 10));
	}

	@Test
	@Rollback(true)
	public void deleteBookFromCart() {
		CartDetails cartDetails = new CartDetails();
		cartDetails.setBook(book1);
		cartDetails.setCart(cart1);
		cartDetails.setQuantity(10);
		cartDetailsList.add(cartDetails);
		
		when(repository.findAll().stream().filter(
				(cartDetail) -> cartDetail.getBook().getId() == book1.getId() && cartDetail.getCart().getId() == cart1.getId())
				.collect(Collectors.toList())).thenReturn(cartDetailsList);
		service.deleteBookFromCart(cart1.getId(), book1.getId());
		verify(repository, times(1)).delete(cartDetails);
	}

}
