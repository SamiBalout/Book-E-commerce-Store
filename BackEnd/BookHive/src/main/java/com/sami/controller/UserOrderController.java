package com.cgi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.dto.AddressDto;
import com.cgi.model.OrderDetails;
import com.cgi.model.Orders;
import com.cgi.service.OrdersService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("user/order")
public class UserOrderController {
	@Autowired
	private OrdersService ordersService;

	@GetMapping("/getByCustomerId/{customerId}")
	public ResponseEntity<List<Orders>> getAllOrdersByCustomerId(@PathVariable("customerId") int customerId) {
		return new ResponseEntity<>(ordersService.getAllOrdersByCustomerId(customerId), HttpStatus.OK);
	}

	@GetMapping("/getOrderDetailsByOrderId/{orderId}")
	public ResponseEntity<List<OrderDetails>> getOrderDetailsByOrderId(@PathVariable("orderId") int orderId) {
		return new ResponseEntity<>(ordersService.getAllOrderDetailsByOrderId(orderId), HttpStatus.OK);
	}

	@PostMapping("/addNewOrder/{cartId}")
	public ResponseEntity<Orders> addNewOrder(@PathVariable("cartId") int cartId, @RequestBody AddressDto addressDto) {
		return new ResponseEntity<>(ordersService.addNewOrder(cartId, addressDto), HttpStatus.CREATED);
	}
}
