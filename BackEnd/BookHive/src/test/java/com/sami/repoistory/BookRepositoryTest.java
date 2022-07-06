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
import com.cgi.repository.BookRepository;
import com.cgi.repository.CartDetailsRepository;
import com.cgi.repository.CartRepository;
import com.cgi.repository.OrderDetailsRepository;
import com.cgi.repository.OrdersRepository;
import com.cgi.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTest {

	@Autowired
	private BookRepository repository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartDetailsRepository cartDetailsRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	private Book book1, book2, book3;

	private List<Book> bookList;

	@BeforeEach
	public void setUp() {

		bookList = new ArrayList<Book>();

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

		book3 = new Book();
		book3.setActive(true);
		book3.setAuthor("Autho2");
		book3.setCategory("Category2");
		book3.setDescription("Description2");
		book3.setFormat("Format2");
		book3.setGenre("Genre2");
		book3.setImageUrl("image.com2");
		book3.setName("Name2");
		book3.setPrice(20.99);
		book3.setQuantity(1);
		book3.setType("Type2");

		orderDetailsRepository.deleteAll();
		orderDetailsRepository.flush();

		ordersRepository.deleteAll();
		ordersRepository.flush();

		cartDetailsRepository.deleteAll();
		cartDetailsRepository.flush();

		cartRepository.deleteAll();
		cartRepository.flush();

		repository.deleteAll();
		repository.flush();
	}

	@Test
	void givenBookSaveTheBook() {
		repository.save(book1);
		Book savedBook = repository.findById(book1.getId()).get();
		assertEquals(book1.getId(), savedBook.getId());
		assertEquals("Author", savedBook.getAuthor());
	}

	@Test
	void givenBookIdDeleteBook() {
		repository.save(book1);
		repository.save(book2);
		repository.deleteById(book1.getId());

		bookList = repository.findAll();
		assertEquals(1, bookList.size());
		assertEquals("Author1", bookList.get(0).getAuthor());
		assertEquals(book2.getId(), bookList.get(0).getId());
	}

	@Test
	void findAllBooks() {
		repository.save(book1);
		repository.save(book2);
		bookList = repository.findAll();
		assertEquals(2, bookList.size());
	}

	@Test
	void updateBookInfo() {
		repository.save(book1);
		Book savedBook = repository.findById(book1.getId()).get();
		assertEquals(book1.getId(), savedBook.getId());
		savedBook.setAuthor("UpdateAuthor");
		repository.save(savedBook);
		Book updateBook = repository.findById(book1.getId()).get();
		assertEquals(book1.getId(), updateBook.getId());
		assertEquals("UpdateAuthor", updateBook.getAuthor());

	}

}
