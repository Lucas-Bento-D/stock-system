package com.marley_store.stock_system.controller.products;

import com.marley_store.stock_system.dto.product.CreateProductDTO;
import com.marley_store.stock_system.dto.product.GetProductDTO;
import com.marley_store.stock_system.dto.product.UpdateProductDTO;
import com.marley_store.stock_system.dto.restMessage.RestDataMessageDTO;
import com.marley_store.stock_system.dto.restMessage.RestMessageDTO;
import com.marley_store.stock_system.model.product.Product;
import com.marley_store.stock_system.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<RestDataMessageDTO<List<GetProductDTO>>> getAllProducts(){
        List<GetProductDTO> products = productService.getAllProducts();
        RestDataMessageDTO<List<GetProductDTO>> restProductDTO = new RestDataMessageDTO<>(HttpStatus.OK.value(), "Products request succesfully", products);
        return ResponseEntity.status(HttpStatus.OK).body(restProductDTO);
    }

    @GetMapping("/get")
    public ResponseEntity<RestDataMessageDTO<GetProductDTO>> getProduct(@RequestParam(name = "codeBar") Long codeBar){
        GetProductDTO product = productService.getProduct(codeBar);
        RestDataMessageDTO<GetProductDTO> restProductDTO = new RestDataMessageDTO<>(HttpStatus.OK.value(), "Product request succesfully", product);
        return ResponseEntity.status(HttpStatus.OK).body(restProductDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<RestMessageDTO> createProduct(@RequestBody CreateProductDTO createProductDTO, HttpServletRequest request){

        productService.createProduct(createProductDTO, request);

        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.OK.value(), "Product created succesfully!");
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    @PatchMapping("/update/{codeBar}")
    public ResponseEntity<RestMessageDTO> updateProduct(@RequestBody UpdateProductDTO updateProductDTO, @PathVariable Long codeBar){
        productService.updateProduct(updateProductDTO, codeBar);
        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.CREATED.value(), "Product updated succesfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }
}
