package com.jawbr.testepratico.customException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PessoaExceptionHandler {


    @ExceptionHandler
    public ResponseEntity<PessoaErrorResponse> handleException(PessoaNotFoundException exc) {

        PessoaErrorResponse error = new PessoaErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<PessoaErrorResponse> handleException(HttpMessageNotReadableException exc) {

        PessoaErrorResponse error = new PessoaErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Invalid request body format. Check your request and try again. ERROR: "+exc.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PessoaErrorResponse> handleException(Exception exc) {

        PessoaErrorResponse error = new PessoaErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
