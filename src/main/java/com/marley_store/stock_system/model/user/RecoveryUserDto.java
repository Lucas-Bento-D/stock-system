package com.marley_store.stock_system.model.user;

import com.marley_store.stock_system.model.role.Role;

import java.util.List;

public record RecoveryUserDto(
        Long id,
        String email,
        List<Role> roles
) {
}
