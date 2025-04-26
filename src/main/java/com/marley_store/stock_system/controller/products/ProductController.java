package com.marley_store.stock_system.controller.products;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product")
public class ProductController {
    @GetMapping("/get")
    public String getProduct(){
        return "online";
    }
}
