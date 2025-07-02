package com.marley_store.stock_system.dto.user;

import com.marley_store.stock_system.dto.product.GetProductDTO;
import com.marley_store.stock_system.model.role.Role;
import com.marley_store.stock_system.model.user.User;

import java.util.List;

public record GetUserDTO(
        Long id,
        String name,
        String email,
        String cnpj,
        List<Role> roles
) {
    public GetUserDTO(User user){
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCnpj(),
                user.getRoles()
        );
    }
}
