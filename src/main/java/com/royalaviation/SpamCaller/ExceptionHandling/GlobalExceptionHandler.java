package com.royalaviation.SpamCaller.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        // Log the exception for more insights
        ex.printStackTrace();

        // You can return a custom error response
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        body.put("timestamp", new Date());

        // Returning the custom error response with HTTP status 500
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Additional specific exception handlers can be added if necessary
    // For example, handling a specific exception like UnauthorizedAccessException:
    // @ExceptionHandler(UnauthorizedAccessException.class)
    // public ResponseEntity<Object> handleUnauthorizedAccess(UnauthorizedAccessException ex, WebRequest request) {
    //     Map<String, Object> body = new HashMap<>();
    //     body.put("status", HttpStatus.FORBIDDEN.value());
    //     body.put("error", "Unauthorized Access");
    //     body.put("message", ex.getMessage());
    //     body.put("timestamp", new Date());
    //     return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    // }
}
