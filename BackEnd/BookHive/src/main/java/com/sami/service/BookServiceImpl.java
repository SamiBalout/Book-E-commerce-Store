package com.cgi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cgi.model.Book;
import com.cgi.repository.BookRepository;


@Service
public class BookServiceImpl implements BookService{
	
	@Autowired 
	private BookRepository repository;

	@Override
	public Book saveBook(Book book) {
		// TODO Auto-generated method stub
		try {
			Optional<Book> newBook = repository.findById(book.getId());
			if(newBook.isEmpty()) {
				return repository.save(book);
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public List<Book> findByCatergory(String category) {
		// TODO Auto-generated method stub
		return repository.findAllByCategory(category);
	}

	@Override
	public boolean deleteBookById(int id) {
		// TODO Auto-generated method stub
		try {
			Optional<Book> deleteBook = repository.findById(id);
			if(deleteBook.isPresent()) {
				repository.deleteById(id);
				return true; 
			}else {
				return false; 
			}
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public Book updateBook(Book book, int id) {
		try {
			Optional<Book> updateBook = repository.findById(id);
			if(updateBook.isPresent()) {
				return repository.save(book);
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public Book getBookById(int id) {
		try {
			Optional<Book> getBook = repository.findById(id);
			if(getBook.isPresent()) {
				return getBook.get();
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}	
	}

	@Override
	public List<Book> findAllBooks() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public boolean updateActive(boolean oldActive) {
		if(oldActive) {
			return false;
		}else {
			return true; 
		}
	}

	@Override
	public List<Book> findAllOnSaleBooks() {
		List<Book> bookList = repository.findAll();
		bookList = bookList.stream().filter((book)->book.isOnSale() == true).collect(Collectors.toList());
		bookList = bookList.stream().filter((book)->book.isActive() == true).collect(Collectors.toList());
		if(bookList.size() >= 6) {
			List<Book> newBookList = new ArrayList<Book>();
			List<Book> tempList = bookList; 
			List<Integer> intList = new ArrayList<Integer>();
			int randomWithMathRandom;
			Book newBook = new Book();
			for(int i = 0; i < 5; i++) {
				randomWithMathRandom = (int) ((Math.random() * (bookList.size() - 0)) + 0);
				while(intList.contains(randomWithMathRandom)) {
					randomWithMathRandom = (int) ((Math.random() * (bookList.size() - 0)) + 0);
				}
				intList.add(randomWithMathRandom);
				newBook = tempList.get(randomWithMathRandom);
				newBookList.add(newBook);
				newBook = null;
			}
		return newBookList;
		}else {
			return bookList;
		}
	}
	

}
