package com.jawbr.testepratico.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EnderecoExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<EnderecoErrorResponse> handleException(EnderecoNotFoundException exc) {
		
		EnderecoErrorResponse error = new EnderecoErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
public ResponseEntity<EnderecoErrorResponse> handleException(Exception exc) {
		
		EnderecoErrorResponse error = new EnderecoErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
