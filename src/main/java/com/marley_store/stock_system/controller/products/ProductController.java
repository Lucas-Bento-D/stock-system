package com.marley_store.stock_system.controller.products;

import com.marley_store.stock_system.dto.product.CreateProductDTO;
import com.marley_store.stock_system.dto.restMessage.RestMessageDTO;
import com.marley_store.stock_system.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get")
    public String getProduct(){
        return "online";
    }

    @PostMapping("/create")
    public ResponseEntity<RestMessageDTO> createProduct(@RequestBody CreateProductDTO createProductDTO){

        productService.createProduct(createProductDTO);

        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.OK.value(), "Product created succesfully!");
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }
}
