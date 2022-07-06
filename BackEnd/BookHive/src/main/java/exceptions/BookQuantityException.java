package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class BookQuantityException extends RuntimeException {
	public BookQuantityException(String message) {
		super(message);
	}
	
	public BookQuantityException(int orderQuantity, int bookQuantity, String bookName) {
		super("Attemping to order " + orderQuantity + " " + bookName + "\'s but only has " + bookQuantity + " in stock");
	}
}
