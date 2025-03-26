package com.marley_store.stock_system.service.user.userDetailsServiceImpl;

import com.marley_store.stock_system.model.user.User;
import com.marley_store.stock_system.model.user.userDetailsImpl.UserDetailsImpl;
import com.marley_store.stock_system.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * O metodo loadUserByUserName é um metodo que carrega informações do usuario a partir de um email.
     * Basicamente ele vai fazer um findo no email, construir um UserDetailsImpl e retornar.
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("usuario nao encontrado"));
        return userRepository.findByEmail(email)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    }

}
