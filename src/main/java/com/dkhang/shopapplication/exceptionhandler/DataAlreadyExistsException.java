package com.dkhang.shopapplication.exceptionhandler;

@SuppressWarnings("serial")
public class DataAlreadyExistsException extends RuntimeException {

	public DataAlreadyExistsException (String message) {
		super(message);
	}
	
}
