package com.cgi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.model.Book;
import com.cgi.model.Cart;
import com.cgi.model.CartDetails;
import com.cgi.model.User;
import com.cgi.repository.BookRepository;
import com.cgi.repository.CartDetailsRepository;
import com.cgi.repository.CartRepository;
import com.cgi.repository.UserRepository;

import exceptions.BookNotFoundException;
import exceptions.BookQuantityException;
import exceptions.CartDetailNotFoundException;
import exceptions.CartExistsException;
import exceptions.CartNotFoundException;
import exceptions.UserNotFoundException;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartDetailsRepository cartDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void deleteCartDetailsByCartId(int cartId) {
		getAllCartDetailsByCartId(cartId).forEach((cartDetails) -> cartDetailsRepository.delete(cartDetails));
	}

	@Override
	public int getCartIdByCustomerId(int customerId) {
		// custom query here would reduce the amount of db calls
		List<Cart> cartList = cartRepository.findAll().stream().filter((cart) -> cart.getUser().getId() == customerId)
				.collect(Collectors.toList());
		if (cartList.isEmpty()) {
			throw new CartNotFoundException("Cart with customer id: " + customerId + " not found");
		}

		return cartList.get(0).getId();
	}

	@Override
	public List<CartDetails> getAllCartDetailsByCartId(int cartId) {
		List<CartDetails> cartDetailsList = cartDetailsRepository.findAll().stream()
				.filter((details) -> details.getCart().getId() == cartId).collect(Collectors.toList());
		if (cartDetailsList.isEmpty()) {
			throw new CartDetailNotFoundException("CartDetail(s) with cart id: " + cartId + " not found");
		}

		return cartDetailsList;
	}

	@Override
	public CartDetails updateBookQuantityByCartDetailsId(int cartId, int bookId, int quantity) {
		if (quantity <= 0) {
			throw new BookQuantityException("Invalid quantity. Quantity must be greater than or equal to one");
		}

		List<CartDetails> cartDetails = cartDetailsRepository.findAll().stream().filter(
				(cartDetail) -> cartDetail.getBook().getId() == bookId && cartDetail.getCart().getId() == cartId)
				.collect(Collectors.toList());
		if (cartDetails.isEmpty()) {
			throw new CartDetailNotFoundException(
					"Cart detail not found with cart id: " + cartId + " and book id: " + bookId);
		}

		cartDetails.get(0).setQuantity(quantity);
		return cartDetailsRepository.save(cartDetails.get(0));
	}

	@Override
	public Cart addNewCart(int userId) {
		List<Cart> userCart = cartRepository.findAll().stream().filter((cart) -> cart.getUser().getId() == userId)
				.collect(Collectors.toList());
		if (!userCart.isEmpty()) {
			throw new CartExistsException(userId);
		}

		Cart cart = new Cart();
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		cart.setUser(user);

		return cartRepository.saveAndFlush(cart);
	}

	@Override
	public CartDetails addNewBookToCart(int bookId, int cartId, int quantity) {
		List<CartDetails> cartDetailsQuery = cartDetailsRepository.findAll().stream().filter(
				(cartDetail) -> cartDetail.getBook().getId() == bookId && cartDetail.getCart().getId() == cartId)
				.collect(Collectors.toList());
		if (cartDetailsQuery.isEmpty()) {
			Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
			Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

			CartDetails cartDetails = new CartDetails();
			cartDetails.setBook(book);
			cartDetails.setCart(cart);
			cartDetails.setQuantity(quantity);

			return cartDetailsRepository.saveAndFlush(cartDetails);
		}

		CartDetails cartDetails = cartDetailsQuery.get(0);
		cartDetails.setQuantity(cartDetails.getQuantity() + 1);

		return cartDetailsRepository.saveAndFlush(cartDetails);
	}

	@Override
	public void deleteBookFromCart(int cartId, int bookId) {
		List<CartDetails> cartDetails = cartDetailsRepository.findAll().stream().filter(
				(cartDetail) -> cartDetail.getBook().getId() == bookId && cartDetail.getCart().getId() == cartId)
				.collect(Collectors.toList());
		if (cartDetails.isEmpty()) {
			throw new CartDetailNotFoundException(
					"Cart detail not found with cart id: " + cartId + " and book id: " + bookId);
		}

		cartDetailsRepository.delete(cartDetails.get(0));
	}
}
