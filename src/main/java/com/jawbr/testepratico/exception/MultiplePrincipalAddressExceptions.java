package com.jawbr.testepratico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MultiplePrincipalAddressExceptions extends RuntimeException {

    private String message;

    public MultiplePrincipalAddressExceptions(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
