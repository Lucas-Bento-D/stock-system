package com.marley_store.stock_system.infra.jwtToken;

import com.marley_store.stock_system.dto.restMessage.RestMessageDTO;
import com.marley_store.stock_system.exceptions.jwtToken.TokenGenerationFailed;
import com.marley_store.stock_system.exceptions.jwtToken.TokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestJwtTokenExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenNotFoundException.class)
    private ResponseEntity<RestMessageDTO> tokenNotFoundException(TokenNotFoundException exception){
        RestMessageDTO errorMessage = new RestMessageDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(TokenGenerationFailed.class)
    private ResponseEntity<RestMessageDTO> tokenGenerationFailed(TokenGenerationFailed exception){
        RestMessageDTO errorMessage = new RestMessageDTO(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
}
