package com.example.testtask.api;

import com.example.testtask.service.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<String> handleRequestException(RequestException ex) {
        return new ResponseEntity<>("Request exception: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
