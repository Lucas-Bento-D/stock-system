package com.marley_store.stock_system.dto.product;

public record CreateProductDTO(
    String name,
    Long quantity,
    Long codeBar,
    Float costPrice,
    Float sellingPrice
) {
}
