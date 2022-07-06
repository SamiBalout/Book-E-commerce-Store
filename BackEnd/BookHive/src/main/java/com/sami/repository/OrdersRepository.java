package com.cgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgi.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

}
