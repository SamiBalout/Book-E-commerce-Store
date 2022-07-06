package com.cgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgi.model.CartDetails;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer> {

}
