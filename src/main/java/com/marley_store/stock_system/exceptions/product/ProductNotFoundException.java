package com.marley_store.stock_system.exceptions.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(){super("Product not found.");}
    public ProductNotFoundException(String message) {
        super(message);
    }
}
