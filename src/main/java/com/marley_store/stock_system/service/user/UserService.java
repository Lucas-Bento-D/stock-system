package com.marley_store.stock_system.service.user;

import com.marley_store.stock_system.config.security.SecurityConfiguration;
import com.marley_store.stock_system.model.role.Role;
import com.marley_store.stock_system.model.user.*;
import com.marley_store.stock_system.model.user.userDetailsImpl.UserDetailsImpl;
import com.marley_store.stock_system.repository.UserRepository;
import com.marley_store.stock_system.service.user.jwtToken.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;


    public List<User> findByName(String name){
        return userRepository.findByName(name);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void createUser(CreateUserDTO createUserDto) {
        String encodedPassword = passwordEncoder.encode(createUserDto.password());
        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(encodedPassword)
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }

    public StatusUser deleteUser(User user){
        User userr = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("Usuario n'ao encontrado"));
//        List<Long> usersId = users.stream().map(user1 -> user1.getId()).collect(Collectors.toList());
        userRepository.deleteById(userr.getId());
        return new StatusUser(204, "User Deleted");
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto){
        System.out.println("Chamando authenticateUser com: " + loginUserDto.email());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
        System.out.println(usernamePasswordAuthenticationToken);

        System.out.println("Autenticação bem-sucedida para: " + loginUserDto.email());


        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }


}
