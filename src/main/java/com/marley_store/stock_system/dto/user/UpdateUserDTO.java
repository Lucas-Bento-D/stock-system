package com.marley_store.stock_system.dto.user;

import com.marley_store.stock_system.model.role.RoleName;

public record UpdateUserDTO(
        String cnpj,
        String name,
        RoleName role
) {
}
