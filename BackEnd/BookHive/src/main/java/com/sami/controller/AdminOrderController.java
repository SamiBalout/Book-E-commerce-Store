package com.cgi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.dto.OrderStatusDto;
import com.cgi.model.OrderDetails;
import com.cgi.model.Orders;
import com.cgi.service.OrdersService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("admin/order")
public class AdminOrderController {

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
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Orders>> getAllOrders() {
		return new ResponseEntity<>(ordersService.getAllOrders(), HttpStatus.OK);
	}

	@PutMapping("/updateStatus/{orderId}")
	public ResponseEntity<Orders> updateOrderStatus(@PathVariable("orderId") int orderId, @RequestBody OrderStatusDto orderStatusDto) {
		return new ResponseEntity<>(ordersService.updateOrderStatus(orderId, orderStatusDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBookFromOrder/{orderId}/{bookId}")
	public ResponseEntity<String> deleteBookFromOrder(@PathVariable("orderId") int orderId, @PathVariable("bookId") int bookId) {
		ordersService.deleteBookFromOrder(orderId, bookId);
		return new ResponseEntity<>("Book removed from order", HttpStatus.OK);
	}
	
	@PutMapping("/updateBookQuantity/{orderId}/{bookId}/{quantity}")
	public ResponseEntity<OrderDetails> updateBookQuantity(@PathVariable("orderId") int orderId, @PathVariable("bookId") int bookId, @PathVariable("quantity") int quantity) {
		return new ResponseEntity<>(ordersService.updateBookQuantityFromOrder(orderId, bookId, quantity), HttpStatus.OK);
	}

}
