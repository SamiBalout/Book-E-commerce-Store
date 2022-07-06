package com.cgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgi.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
