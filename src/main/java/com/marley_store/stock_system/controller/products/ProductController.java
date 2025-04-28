package com.marley_store.stock_system.controller.products;

import com.marley_store.stock_system.dto.product.CreateProductDTO;
import com.marley_store.stock_system.dto.restMessage.RestDataMessageDTO;
import com.marley_store.stock_system.dto.restMessage.RestMessageDTO;
import com.marley_store.stock_system.model.product.Product;
import com.marley_store.stock_system.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<RestDataMessageDTO<List<Product>>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        RestDataMessageDTO<List<Product>> restProductDTO = new RestDataMessageDTO<>(HttpStatus.OK.value(), "Products request succesfully", products);
        return ResponseEntity.status(HttpStatus.OK).body(restProductDTO);
    }

    @GetMapping("/get")
    public ResponseEntity<RestDataMessageDTO<List<Product>>> getProduct(@RequestParam(name = "codeBar") Long codeBar){
        List<Product> products = productService.getProduct(codeBar);
        RestDataMessageDTO<List<Product>> restProductDTO = new RestDataMessageDTO<>(HttpStatus.OK.value(), "Product request succesfully", products);
        return ResponseEntity.status(HttpStatus.OK).body(restProductDTO);
    }



    @PostMapping("/create")
    public ResponseEntity<RestMessageDTO> createProduct(@RequestBody CreateProductDTO createProductDTO){

        productService.createProduct(createProductDTO);

        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.OK.value(), "Product created succesfully!");
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }
}
