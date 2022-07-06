package com.cgi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;

import com.cgi.model.Book;
import com.cgi.repository.BookRepository;

public class BookServiceTest {
	
	@Mock
	private BookRepository repository;
	
	@InjectMocks 
	private BookServiceImpl service; 
	
	private Book book1, book2, book3; 
	
	private List<Book> bookList;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
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
		book3.setId(3);
		
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		book1 = null;
		bookList = null;
	}
	
	@Test
	@Rollback(true)
	public void testAddNewBook() {
		when(repository.findById(any())).thenReturn(Optional.empty());
		when(repository.save(any())).thenReturn(book1);
		assertEquals(book1, service.saveBook(book1));
		verify(repository, times(1)).save(any());
		
	}
	@Test
	@Rollback(true)
	public void testAddNewBookFailure() {
		when(repository.findById(any())).thenReturn(Optional.of(book1));
		when(repository.save(any())).thenReturn(book1);
		assertEquals(null, service.saveBook(book1));
		verify(repository, times(0)).save(any());
	}
	
	@Test
	@Rollback(true)
	public void testUpdateBook() {
		Book updateBook = book1; 
		updateBook.setName("Update");
		when(repository.findById(any())).thenReturn(Optional.of(updateBook));
		when(repository.save(any())).thenReturn(updateBook);
		
		assertEquals(updateBook, service.updateBook(updateBook, updateBook.getId()));
		Optional<Book> checkBook = repository.findById(updateBook.getId());
		assertEquals("Update", checkBook.get().getName());
		verify(repository, times(1)).save(any());
		
		
		
	}
	
	@Test
	@Rollback(true)
	public void testUpdateBookFailure() {
		Book updateBook = book1; 
		updateBook.setName("Update");
		when(repository.findById(any())).thenReturn(Optional.empty());
		when(repository.save(any())).thenReturn(updateBook);
		assertEquals(null, service.updateBook(updateBook, updateBook.getId()));
		verify(repository, times(0)).save(any());
		
	}
	
//	@Test
//	@Rollback(true)
//	public void testFindBookByCategory() {
//		
//	}
	
	
	@Test
	@Rollback(true)
	public void testDeleteBookById() {
		when(repository.findById(any())).thenReturn(Optional.of(book1));
		service.deleteBookById(book1.getId());
		
		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).deleteById(any());
	}
	
	@Test
	@Rollback(true)
	public void testDeleteBookByIdFailure() {
		when(repository.findById(any())).thenReturn(Optional.empty());
		service.deleteBookById(book1.getId());
		
		verify(repository, times(1)).findById(any());
		verify(repository, times(0)).deleteById(any());
		
	}
	
	@Test
	@Rollback(true)
	public void testGetBookById() {
		when(repository.findById(any())).thenReturn(Optional.of(book1));
		assertEquals(book1, service.getBookById(book1.getId()));
		verify(repository, times(1)).findById(any());
		
	}
	
	@Test
	@Rollback(true)
	public void testGetBookByIdFailure() {
		when(repository.findById(any())).thenReturn(Optional.empty());
		assertEquals(null, service.getBookById(book1.getId()));
		verify(repository, times(1)).findById(any());
		
	}
	
	
	@Test
	@Rollback(true)
	public void testFindAllBooks() {
		assertEquals(bookList, service.findAllBooks());
	}
	
	@Test
	@Rollback(true)
	public void testUpdateActiveWhenActiveIsTrue() {
		assertEquals(false, service.updateActive(true));
		
	}
	
	@Test
	@Rollback(true)
	public void testUpdateActiveWhenActiveIsFalse() {
		assertEquals(true, service.updateActive(false));
	}
	
	

}
