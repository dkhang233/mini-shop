package com.dkhang.shopapplication.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FileException extends RuntimeException {
	public FileException(String message) {
		super(message);
	}
}
