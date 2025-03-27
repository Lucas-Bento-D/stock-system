package com.marley_store.stock_system.dto.user;

import com.marley_store.stock_system.model.role.Role;

import java.util.List;

public record RecoveryUserDTO(
        Long id,
        String email,
        List<Role> roles
) {
}
