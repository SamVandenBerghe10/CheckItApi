package be.vives.ti.CheckIt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String id;
    public ResourceNotFoundException(String id) {
        super(String.format("Resource not found : '%s'",id));
    }
    public String getId() {
        return this.id;
    }
}
