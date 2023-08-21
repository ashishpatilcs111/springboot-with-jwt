package com.boot.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiError {
	
	private LocalDateTime date;
	private HttpStatus status;
	private String message;
	private String details;
	
	public ApiError(LocalDateTime date, HttpStatus status, String message, String details) {
		super();
		this.date = date;
		this.status = status;
		this.message = message;
		this.details = details;
	}

	

}
