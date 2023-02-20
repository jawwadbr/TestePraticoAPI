package com.jawbr.testepratico.attornatus.customException;

@SuppressWarnings("serial")
public class EnderecoNotFoundException extends RuntimeException {

	public EnderecoNotFoundException() {
	}

	public EnderecoNotFoundException(String message) {
		super(message);
	}

	public EnderecoNotFoundException(Throwable cause) {
		super(cause);
	}

	public EnderecoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EnderecoNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
