package com.marley_store.stock_system.infra.product;

import com.marley_store.stock_system.dto.restMessage.RestMessageDTO;
import com.marley_store.stock_system.exceptions.product.ProductNotFoundException;
import com.marley_store.stock_system.infra.user.RestUserExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class RestProductExceptionHandler extends RestUserExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<RestMessageDTO> productNotFoundException(ProductNotFoundException exception){
        RestMessageDTO errorMessage = new RestMessageDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

}
