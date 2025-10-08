package com.sr.inventory_tracker.error;

import com.sr.inventory_tracker.model.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> productNotFoundException(ProductNotFoundException productNotFoundException) {

        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, productNotFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorMessage> supplierNotFoundException(SupplierNotFoundException supplierNotFoundException) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, supplierNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> categoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, categoryNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ErrorMessage> usernameExistsException(UsernameExistsException usernameExistsException) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, usernameExistsException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorMessage message = new ErrorMessage(HttpStatus.UNAUTHORIZED, "JWT Token has expired. Please login again.");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, "An error occurred with the JWT token");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
