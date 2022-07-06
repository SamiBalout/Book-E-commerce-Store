package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CartDetailNotFoundException extends RuntimeException  {
	public CartDetailNotFoundException(String message) {
		super(message);
	}
	
	public CartDetailNotFoundException(int cartDetailsId) {
		super("CartDetail with id: " + cartDetailsId + " not found");
	}
}
