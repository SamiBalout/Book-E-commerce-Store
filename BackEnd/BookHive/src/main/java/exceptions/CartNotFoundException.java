package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException {
	public CartNotFoundException(String message) {
		super(message);
	}
	
	public CartNotFoundException(int cartId) {
		super("Cart with id: " + cartId + " not found");
	}
}
