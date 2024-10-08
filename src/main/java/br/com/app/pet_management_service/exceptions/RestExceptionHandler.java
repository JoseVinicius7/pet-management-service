package br.com.app.pet_management_service.exceptions;

import br.com.app.pet_management_service.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    public static final String NO_DETAILS_AVAILABLE = "No details available";

    @ExceptionHandler(PayloadValidationException.class)
    public ResponseEntity<ErrorResponse> handlePayloadValidationException(PayloadValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(ex, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(PetNotFoundException .class)
    public ResponseEntity<ErrorResponse> handlePetNotFoundException (PetNotFoundException  ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(ex, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ErrorResponse createErrorResponse(Exception ex, HttpStatus status) {
        return new ErrorResponse(
                status.name(),
                ex.getClass().getSimpleName(),
                ex.getMessage() != null ? ex.getMessage() : NO_DETAILS_AVAILABLE
        );
    }
}
