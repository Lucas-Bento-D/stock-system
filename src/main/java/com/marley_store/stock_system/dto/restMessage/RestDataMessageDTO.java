package com.marley_store.stock_system.dto.restMessage;

public record RestDataMessageDTO<T>(
        int status,
        String message,
        T data
) {
}
