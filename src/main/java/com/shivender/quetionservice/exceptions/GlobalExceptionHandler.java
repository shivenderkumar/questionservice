package com.shivender.quetionservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String> handleApiException(ApiException apiException){
        return new ResponseEntity<>(apiException.getMessage(), apiException.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleApiException(Exception exception){
        return new ResponseEntity<>("Server internal error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
