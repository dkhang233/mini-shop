package com.dkhang.shopapplication.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.UnexpectedTypeException;

@ControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDetails> handlerException(Exception ex, WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> allMessages = ex.getFieldErrors().stream().map(fe -> fe.getDefaultMessage()).toList();
		return new ResponseEntity<Object>(allMessages, status);
	}


	@ExceptionHandler(UnexpectedTypeException.class)
	public ResponseEntity<ExceptionDetails> handleUnexpectedTypeException(UnexpectedTypeException ex,
			WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ExceptionDetails> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
		ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ExceptionDetails>(exceptionDetails, HttpStatus.NOT_FOUND);
	}

}
