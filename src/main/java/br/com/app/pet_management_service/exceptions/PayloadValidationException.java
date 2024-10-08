package br.com.app.pet_management_service.exceptions;

public class PayloadValidationException extends RuntimeException {
    public PayloadValidationException(String message) {
        super(message);
    }

    public PayloadValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
