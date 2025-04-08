package com.marley_store.stock_system.model.user.userDetailsImpl;

import com.marley_store.stock_system.model.user.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Null user");
        }
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
         Este método converte a lista de papéis (roles) associados ao usuário
         em uma coleção de GrantedAuthorities, que é a forma que o Spring Security
         usa para representar papéis. Isso é feito mapeando cada papel para um
         novo SimpleGrantedAuthority, que é uma implementação simples de
         GrantedAuthority
        */

        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return org.springframework.security.core.userdetails.UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return org.springframework.security.core.userdetails.UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return org.springframework.security.core.userdetails.UserDetails.super.isEnabled();
    }


}
