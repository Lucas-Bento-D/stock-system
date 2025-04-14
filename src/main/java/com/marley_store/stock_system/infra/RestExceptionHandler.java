package com.marley_store.stock_system.infra;

import com.marley_store.stock_system.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> userNotFoundException(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}
