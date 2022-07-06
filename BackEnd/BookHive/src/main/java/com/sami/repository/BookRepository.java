package com.cgi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findAllByCategory(String category);

}
