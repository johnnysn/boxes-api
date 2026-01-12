package com.uriel.boxes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponseDTO handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Resource not found", null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ErrorResponseDTO handleBadRequest(BadRequestException ex) {
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Bad request", null);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ErrorResponseDTO handleForbidden(ForbiddenException ex) {
        return new ErrorResponseDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), "Forbidden", null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Itera sobre todos os erros encontrados na validação
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ErrorResponseDTO(
                "Erro de validação nos campos informados!",
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errors
        );
    }

    public record ErrorResponseDTO(
            String message,
            LocalDateTime timestamp,
            int status,
            String error,
            Map<String, String> fieldErrors
    ) {}
}
