package be.vives.ti.CheckIt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeadlineParseException extends RuntimeException {
    private String message;
    public DeadlineParseException(String message) {
        super(String.format("deadline not 'yyyy-MM-dd HH:mm:ss' : '%s'",message));
    }
    public String getMessage() {
        return this.message;
    }
}
