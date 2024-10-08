package br.com.app.pet_management_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PayloadValidationException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;

    public PayloadValidationException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public PayloadValidationException(String message, HttpStatus status) {
        super(message);
        this.errorCode = status.name();
        this.status = status;
    }
}
