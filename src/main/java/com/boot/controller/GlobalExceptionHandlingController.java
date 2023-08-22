package com.boot.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlingController {
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex) {
        String details = ex.getMessage();
        ApiError err = new ApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST, 
            "Resource Not Found" ,
            details);
        
        return ResponseEntity.badRequest().body(err);
    }

}
