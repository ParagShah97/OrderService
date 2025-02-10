package com.parag.OrderService.exceptions;

import com.parag.OrderService.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
* RestResponseEntityExceptionHandler: is a controller advice which will return an
* ResponseEntity<ErrorResponse> instance.
* */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleOrderServiceException(CustomException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(),
                exception.getErrorCode()), HttpStatus.valueOf(exception.getStatus()));
    }
}
