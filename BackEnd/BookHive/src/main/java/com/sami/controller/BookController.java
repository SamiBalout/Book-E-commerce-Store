package com.cgi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cgi.model.Book;
import com.cgi.model.Cart;
import com.cgi.service.BookService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("book")
public class BookController {
	
	@Autowired 
	private BookService service; 
	
	
	@PostMapping("/add")
	public ResponseEntity<?> addBook(@RequestBody Book book){
		book.setActive(true);
		Book newBook = service.saveBook(book);
		if(newBook != null) {
			return new ResponseEntity<>(book,HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("Error with adding book",HttpStatus.CONFLICT);
		}
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable("id") int id){
		try {
			book.setId(id);
			Book newBook = service.updateBook(book, id);
			if(newBook != null) {
				return new ResponseEntity<>(newBook,HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Error with adding book",HttpStatus.NOT_FOUND);	
			}
		}catch(Exception e) {
			return new ResponseEntity<>("Error with updating book",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/update/quantity/{quantity}/{id}")
	public ResponseEntity<?> updateBookQuantity(@PathVariable("quantity") int quantity, @PathVariable("id") int id){
		try {
			Book newBook = service.getBookById(id);
			if(newBook != null) {
				if(newBook.getQuantity() >= quantity) {
				newBook.setQuantity(newBook.getQuantity() - quantity);
				service.updateBook(newBook, id);
				return new ResponseEntity<>(newBook,HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Error with quanity. Stock level is " + newBook.getQuantity(),HttpStatus.CONFLICT);
			}
			}else {
				return new ResponseEntity<>("Error with adding book",HttpStatus.NOT_FOUND);	
			}
		}catch(Exception e) {
			return new ResponseEntity<>("Error with updating book",HttpStatus.NOT_FOUND);
		}
		
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable("id") int id){
		try {
			Book newBook = service.getBookById(id);
			if(newBook != null) {
				boolean newActive = service.updateActive(newBook.isActive());
				newBook.setActive(newActive);
				Book updatedBook = service.updateBook(newBook, id);
				if(updatedBook != null) {
					return new ResponseEntity<>("Deleted Book with id of " + id ,HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Error with deleting book with id " + id,HttpStatus.NOT_FOUND);
				}
			}else {
				return new ResponseEntity<>("Error with deleting book with id " + id,HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>("Error with deleting book with id " + id,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<?> getBook (@PathVariable("id") int id){
		try {
			Book newBook = service.getBookById(id);
			if(newBook != null) {
				return new ResponseEntity<>(newBook,HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Error with getting book with id " + id,HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>("Error with getting book with id " + id,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllBooks(){
		try {
			List<Book> bookList = service.findAllBooks();
			return new ResponseEntity<>(bookList,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error with getting book list ",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAllOnSale")
	public ResponseEntity<?> getAllBooksOnSale(){
		try {
			List<Book> bookList = service.findAllOnSaleBooks();
			return new ResponseEntity<>(bookList,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error with getting book list ",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAllOnSaleAll")
	public ResponseEntity<?> getAllBooksOnSaleAll(){
		try {
			List<Book> bookList = service.findAllBooks();
			bookList = bookList.stream().filter((book)->book.isActive() == true).collect(Collectors.toList());
			bookList = bookList.stream().filter((book)->book.isOnSale() == true).collect(Collectors.toList());
			return new ResponseEntity<>(bookList,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error with getting book list ",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAllByCategory/{cat}")
	public ResponseEntity<?> getAllByCategory(@PathVariable("cat") String cat){
		try {
			cat = cat.trim();
			List<Book> bookList = service.findByCatergory(cat);
			bookList = bookList.stream().filter((book)->book.isActive() == true).collect(Collectors.toList());
			return new ResponseEntity<>(bookList,HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error with getting book list ",HttpStatus.NOT_FOUND);
		}
	}

}
