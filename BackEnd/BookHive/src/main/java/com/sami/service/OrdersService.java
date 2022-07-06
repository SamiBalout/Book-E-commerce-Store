package com.cgi.service;

import java.util.List;

import com.cgi.dto.AddressDto;
import com.cgi.dto.OrderStatusDto;
import com.cgi.model.OrderDetails;
import com.cgi.model.Orders;

public interface OrdersService {

	List<OrderDetails> getAllOrderDetailsByOrderId(int orderId);

	Orders addNewOrder(int cartId, AddressDto addressDto);

	Orders updateOrderStatus(int orderId, OrderStatusDto orderStatusDto);

	OrderDetails findOrderDetails(int orderId, int bookId);

	void deleteBookFromOrder(int orderId, int bookId);

	OrderDetails updateBookQuantityFromOrder(int orderId, int bookId, int quantity);

	List<Orders> getAllOrdersByCustomerId(int customerId);

	List<Orders> getAllOrders();

}
