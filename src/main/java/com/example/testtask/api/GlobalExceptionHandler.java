package com.example.testtask.api;

import com.example.testtask.service.RequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorResponse> handleRequestException(HttpServletRequest request, RequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ZonedDateTime.now(), 400, ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(ZonedDateTime.now(),
                            400,
                            String.valueOf(ex.getDetailMessageArguments()[1]),
                            request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handlePSQLException(HttpServletRequest request, PSQLException ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(ZonedDateTime.now(),
                        400,
                        ex.getServerErrorMessage().getMessage(),
                        request.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
