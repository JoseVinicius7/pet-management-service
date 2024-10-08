package br.com.app.pet_management_service.exceptions.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String errorName;
    private String errorMessage;
}

