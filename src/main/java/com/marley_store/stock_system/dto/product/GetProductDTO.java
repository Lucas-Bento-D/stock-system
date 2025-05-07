package com.marley_store.stock_system.dto.product;

import com.marley_store.stock_system.model.product.Product;

public record GetProductDTO(
        Long id,
        String name,
        Long quantity,
        Long codeBar,
        Float costPrice,
        Float sellingPrice,
        Long userId
) {
    public GetProductDTO(Product product){
        this(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getCodeBar(),
                product.getCostPrice(),
                product.getSellingPrice(),
                product.getUser() != null ? product.getUser().getId() : null
        );
    }
}
