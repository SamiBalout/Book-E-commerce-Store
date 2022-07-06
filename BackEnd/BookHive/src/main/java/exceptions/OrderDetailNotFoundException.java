package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OrderDetailNotFoundException extends RuntimeException  {
	public OrderDetailNotFoundException(String message) {
		super(message);
	}
	
	public OrderDetailNotFoundException(int orderId, int bookId) {
		super("OrderDetail with order id: " + orderId +  " and book id: " + bookId + " not found");
	}
	
	public OrderDetailNotFoundException(int orderId) {
		super("OrderDetail with order id: " + orderId + " not found");
	}
}
