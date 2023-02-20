package com.jawbr.testepratico.attornatus.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PessoaExceptionHandler {

	// Exception para caso não encontrar uma Pessoa
	@ExceptionHandler
	public ResponseEntity<PessoaErrorResponse> handleException(PessoaNotFoundException exc) {
		
		PessoaErrorResponse error = new PessoaErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	//Exception Generica, pegar qualquer erro
	@ExceptionHandler
	public ResponseEntity<PessoaErrorResponse> handleException(Exception exc) {
		
		PessoaErrorResponse error = new PessoaErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
