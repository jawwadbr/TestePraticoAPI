package com.jawbr.testepratico.attornatus.customException;

@SuppressWarnings("serial")
public class PessoaNotFoundException extends RuntimeException {

	public PessoaNotFoundException() {}

	public PessoaNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PessoaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PessoaNotFoundException(String message) {
		super(message);
	}

	public PessoaNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
