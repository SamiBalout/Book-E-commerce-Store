package com.cgi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cgi.model.Book;

public interface BookService {
	
	public Book saveBook(Book book);
	
	public List<Book> findByCatergory(String category);
	
	public boolean deleteBookById(int id);
	
	public Book updateBook(Book book, int id);
	
	public Book getBookById(int id);
	
	public List<Book> findAllBooks();
	
	public boolean updateActive(boolean oldActive);
	
	public List<Book> findAllOnSaleBooks();
	

}
