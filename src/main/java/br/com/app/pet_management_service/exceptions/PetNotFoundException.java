package br.com.app.pet_management_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PetNotFoundException  extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;

    public PetNotFoundException (String message) {
        this(message, HttpStatus.NOT_FOUND);
    }

    public PetNotFoundException (String message, HttpStatus status) {
        super(message);
        this.errorCode = status.name();
        this.status = status;
    }
}
