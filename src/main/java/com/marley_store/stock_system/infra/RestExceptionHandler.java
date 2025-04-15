package com.marley_store.stock_system.infra;

import com.marley_store.stock_system.exceptions.jwtToken.TokenNotFoundException;
import com.marley_store.stock_system.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestMessage> userNotFoundException(UserNotFoundException exception){
        RestMessage errorMessage = new RestMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    private ResponseEntity<RestMessage> tokenNotFoundException(TokenNotFoundException exception){
        RestMessage errorMessage = new RestMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
