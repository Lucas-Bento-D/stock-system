package com.marley_store.stock_system.dto.product;

public record UpdateProductDTO(
        String name,
        Float costPrice,
        Float sellingPrice
) {
}
