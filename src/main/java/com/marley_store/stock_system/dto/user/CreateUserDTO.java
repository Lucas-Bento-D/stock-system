package com.marley_store.stock_system.dto.user;

import com.marley_store.stock_system.model.role.RoleName;

import java.util.Optional;

public record CreateUserDTO(
        String cnpj,
        String name,
        String email,
        String password,
        RoleName role
) {
}
