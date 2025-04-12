package com.marley_store.stock_system.dto.user;

public record UpdatePasswordDTO(
        String password,
        String confirmPassword
) {
}
