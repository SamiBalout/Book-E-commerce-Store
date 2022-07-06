package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
	public OrderNotFoundException(String message) {
		super(message);
	}
	
	public OrderNotFoundException(int orderId) {
		super("Order with id: " + orderId + " not found");
	}
}
