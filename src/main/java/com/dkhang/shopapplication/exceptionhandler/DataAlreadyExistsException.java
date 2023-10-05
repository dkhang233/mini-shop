package com.dkhang.shopapplication.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DataAlreadyExistsException extends RuntimeException {

	public DataAlreadyExistsException (String message) {
		super(message);
	}
	
}
