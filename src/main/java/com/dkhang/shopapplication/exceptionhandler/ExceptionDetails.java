package com.dkhang.shopapplication.exceptionhandler;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionDetails {
	
	private LocalDateTime timestamp;
	private String message;
	private String details;
	
}
