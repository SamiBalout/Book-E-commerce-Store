package com.cgi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.model.Cart;
import com.cgi.model.CartDetails;
import com.cgi.service.CartService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("user/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@GetMapping("/getCartIdByCustomerId/{customerId}")
	public ResponseEntity<Integer> getAllCartsByCustomerId(@PathVariable("customerId") int customerId) {
		return new ResponseEntity<>(cartService.getCartIdByCustomerId(customerId), HttpStatus.OK);
	}

	@PutMapping("/updateBookInCart/{cartId}/{bookId}/{quantity}")
	public ResponseEntity<CartDetails> updateBookQuantityByCartDetailsId(@PathVariable("cartId") int cartId,
			@PathVariable("bookId") int bookId, @PathVariable("quantity") int quantity) {
		return new ResponseEntity<>(cartService.updateBookQuantityByCartDetailsId(cartId, bookId, quantity),
				HttpStatus.OK);
	}

	@DeleteMapping("/deleteBookFromCart/{cartId}/{bookId}")
	public ResponseEntity<String> deleteBookFromCart(@PathVariable("cartId") int cartId,
			@PathVariable("bookId") int bookId) {
		cartService.deleteBookFromCart(cartId, bookId);
		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	@DeleteMapping("/deleteCartDetailsByCartId/{cartId}")
	public ResponseEntity<String> deleteCartDetailsByCartId(@PathVariable("cartId") int cartId) {
		cartService.deleteCartDetailsByCartId(cartId);
		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	@PostMapping("/addNewCartForUser/{userId}")
	public ResponseEntity<Cart> addNewCart(@PathVariable("userId") int userId) {
		return new ResponseEntity<>(cartService.addNewCart(userId), HttpStatus.CREATED);
	}

	@PostMapping("/addBookToCart/{bookId}/{cartId}/{quantity}")
	public ResponseEntity<CartDetails> addBookToCart(@PathVariable("bookId") int bookId,
			@PathVariable("cartId") int cartId, @PathVariable("quantity") int quantity) {
		return new ResponseEntity<>(cartService.addNewBookToCart(bookId, cartId, quantity), HttpStatus.CREATED);
	}

	@GetMapping("/getAllCartDetailsByCartId/{cartId}")
	public ResponseEntity<List<CartDetails>> getAllCartDetailsByCartId(@PathVariable("cartId") int cartId) {
		return new ResponseEntity<>(cartService.getAllCartDetailsByCartId(cartId), HttpStatus.OK);
	}
}
