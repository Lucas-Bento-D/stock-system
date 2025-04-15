package com.marley_store.stock_system.infra.user;

import com.marley_store.stock_system.dto.restMessage.RestMessageDTO;
import com.marley_store.stock_system.exceptions.jwtToken.TokenNotFoundException;
import com.marley_store.stock_system.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestUserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestMessageDTO> userNotFoundException(UserNotFoundException exception){
        RestMessageDTO errorMessage = new RestMessageDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
