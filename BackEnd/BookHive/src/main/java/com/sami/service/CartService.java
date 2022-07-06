package com.cgi.service;

import java.util.List;

import com.cgi.model.Cart;
import com.cgi.model.CartDetails;

public interface CartService {


	public List<CartDetails> getAllCartDetailsByCartId(int cartId);

	public CartDetails updateBookQuantityByCartDetailsId(int cartId, int bookId, int quantity);

	public Cart addNewCart(int userId);

	public CartDetails addNewBookToCart(int bookId, int cartId, int quantity);

	int getCartIdByCustomerId(int customerId);

	public void deleteBookFromCart(int cartId, int bookId);

	public void deleteCartDetailsByCartId(int cartId);

}
