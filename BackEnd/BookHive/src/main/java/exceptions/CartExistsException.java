package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class CartExistsException extends RuntimeException {
	public CartExistsException(int userId) {
		super("Cart already exists for user: " + userId);
	}
}
