package com.jawbr.testepratico.exception.handler;

import com.jawbr.testepratico.exception.InvalidParameterException;
import com.jawbr.testepratico.exception.MultiplePrincipalAddressExceptions;
import com.jawbr.testepratico.exception.errorResponse.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InvalidParameterException exc) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(MultiplePrincipalAddressExceptions exc) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
