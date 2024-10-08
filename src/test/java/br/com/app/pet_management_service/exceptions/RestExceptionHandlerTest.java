package br.com.app.pet_management_service.exceptions;

import br.com.app.pet_management_service.exceptions.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setUp() {
        restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    void handlePayloadValidationException_ShouldReturnBadRequest() {
        String message = "O nome do pet é obrigatório.";
        PayloadValidationException exception = new PayloadValidationException(message);

        ResponseEntity<ErrorResponse> response = restExceptionHandler.handlePayloadValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("BAD_REQUEST", response.getBody().getErrorCode());
        assertEquals("PayloadValidationException", response.getBody().getErrorName());
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    void handlePetNotFoundException_ShouldReturnNotFound() {
        String message = "Pet não encontrado.";
        PetNotFoundException exception = new PetNotFoundException(message);

        ResponseEntity<ErrorResponse> response = restExceptionHandler.handlePetNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("NOT_FOUND", response.getBody().getErrorCode());
        assertEquals("PetNotFoundException", response.getBody().getErrorName());
        assertEquals(message, response.getBody().getErrorMessage());
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {
        Exception exception = new Exception("Erro inesperado");

        ResponseEntity<ErrorResponse> response = restExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getErrorCode());
        assertEquals("Exception", response.getBody().getErrorName());
        assertEquals("Erro inesperado", response.getBody().getErrorMessage());
    }

}
