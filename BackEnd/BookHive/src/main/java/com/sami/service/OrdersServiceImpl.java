package com.cgi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.dto.AddressDto;
import com.cgi.dto.OrderStatusDto;
import com.cgi.model.Book;
import com.cgi.model.Cart;
import com.cgi.model.CartDetails;
import com.cgi.model.OrderDetails;
import com.cgi.model.Orders;
import com.cgi.repository.BookRepository;
import com.cgi.repository.CartRepository;
import com.cgi.repository.OrderDetailsRepository;
import com.cgi.repository.OrdersRepository;
import com.cgi.repository.UserRepository;

import exceptions.BookQuantityException;
import exceptions.OrderDetailNotFoundException;
import exceptions.OrderNotFoundException;

@Service
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private CartService cartService;
	
	@Override
	public Orders addNewOrder(int cartId, AddressDto addressDto) {
		List<CartDetails> cartDetailsList = cartService.getAllCartDetailsByCartId(cartId);
		Orders orders = new Orders();
		orders.setStatus("Pending");
		orders.setAddress(addressDto.getAddress());
		orders.setDateTime(LocalDateTime.now());
		orders.setUser(cartDetailsList.get(0).getCart().getUser());

		Orders savedOrder = ordersRepository.saveAndFlush(orders);
		cartDetailsList.forEach((cartDetail) -> {
			OrderDetails orderDetails = new OrderDetails();
			Book book = cartDetail.getBook();

			if (book.getQuantity() < cartDetail.getQuantity()) {
				throw new BookQuantityException(cartDetail.getQuantity(), book.getQuantity(), book.getName());
			}

			orderDetails.setOrder(orders);
			orderDetails.setBook(book);
			orderDetails.setQuantity(cartDetail.getQuantity());
			book.setQuantity(book.getQuantity() - cartDetail.getQuantity());
			orderDetailsRepository.saveAndFlush(orderDetails);
			bookRepository.saveAndFlush(book);
		});

		cartService.deleteCartDetailsByCartId(cartId);

		return savedOrder;
	}

	@Override
	public Orders updateOrderStatus(int orderId, OrderStatusDto orderStatusDto) {
		Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
		order.setStatus(orderStatusDto.getStatus());
		return ordersRepository.save(order);
	}

	@Override
	public OrderDetails findOrderDetails(int orderId, int bookId) {
		// custom query here would reduce the amount of db calls
		List<OrderDetails> orderDetails = orderDetailsRepository.findAll().stream().filter(
				(orderDetail) -> orderDetail.getOrder().getId() == orderId && orderDetail.getBook().getId() == bookId)
				.collect(Collectors.toList());
		if (orderDetails.isEmpty()) {
			throw new OrderDetailNotFoundException(orderId, bookId);
		}

		return orderDetails.get(0);
	}

	@Override
	public void deleteBookFromOrder(int orderId, int bookId) {
		OrderDetails orderDetail = findOrderDetails(orderId, bookId);
		Book book = orderDetail.getBook();
		book.setQuantity(book.getQuantity() + orderDetail.getQuantity());
		bookRepository.saveAndFlush(book);
		orderDetailsRepository.delete(orderDetail);
	}

	@Override
	public OrderDetails updateBookQuantityFromOrder(int orderId, int bookId, int quantity) {
		if (quantity == 0) {
			throw new BookQuantityException("Attempting to set quantity to 0, please use delete instead");
		}
		
		OrderDetails orderDetail = findOrderDetails(orderId, bookId);
		Book book = orderDetail.getBook();
		if (book.getQuantity() < quantity) {
			throw new BookQuantityException(quantity, book.getQuantity(), book.getName());
		}

		int difference = quantity - orderDetail.getQuantity();
		book.setQuantity(book.getQuantity() - difference);
		bookRepository.saveAndFlush(book);

		orderDetail.setQuantity(quantity);
		return orderDetailsRepository.saveAndFlush(orderDetail);
	}

	@Override
	public List<Orders> getAllOrdersByCustomerId(int customerId) {
		List<Orders> orderList = ordersRepository.findAll().stream()
				.filter((order) -> order.getUser().getId() == customerId).collect(Collectors.toList());
		if (orderList.isEmpty()) {
			throw new OrderNotFoundException("Order(s) with customer id: " + customerId + " not found");
		}

		return orderList;
	}

	@Override
	public List<OrderDetails> getAllOrderDetailsByOrderId(int orderId) {
		List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll().stream()
				.filter((orderDetail) -> orderDetail.getOrder().getId() == orderId).collect(Collectors.toList());
		if (orderDetailsList.isEmpty()) {
			throw new OrderDetailNotFoundException(orderId);
		}

		return orderDetailsList;
	}

	@Override
	public List<Orders> getAllOrders() {
		return ordersRepository.findAll();
	}
}
