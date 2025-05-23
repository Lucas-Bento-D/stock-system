package com.marley_store.stock_system.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marley_store.stock_system.config.security.SecurityConfiguration;
import com.marley_store.stock_system.config.userAuthenticationFilter.UserAuthenticationFilter;
import com.marley_store.stock_system.dto.user.*;
import com.marley_store.stock_system.dto.jwtToken.RecoveryJwtTokenDTO;
import com.marley_store.stock_system.exceptions.jwtToken.TokenGenerationFailed;
import com.marley_store.stock_system.exceptions.user.UserNotFoundException;
import com.marley_store.stock_system.model.role.Role;
import com.marley_store.stock_system.model.user.*;
import com.marley_store.stock_system.model.user.userDetailsImpl.UserDetailsImpl;
import com.marley_store.stock_system.repository.user.UserRepository;
import com.marley_store.stock_system.service.jwtToken.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;


    public GetUserDTO findByEmail(HttpServletRequest request){
        String email = userAuthenticationFilter.getEmailToken(request);
        Optional<User> user = userRepository.findByEmail(email);

        if(!user.isPresent()) throw new UserNotFoundException();

        return new GetUserDTO(user.get());
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

    public void deleteUser(HttpServletRequest request){
        String email = userAuthenticationFilter.getEmailToken(request);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        userRepository.deleteById(user.getId());
    }

    public RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDto){
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
        }catch (BadCredentialsException e) {
            throw new TokenGenerationFailed("Invalid credentials");
        }
    }

    public void updateUser(UpdateUserDTO updateUserDTO, HttpServletRequest request) throws JsonProcessingException {

        String email = userAuthenticationFilter.getEmailToken(request);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectNode userObject = objectMapper.valueToTree(user);
        ObjectNode createUserDTOObject = objectMapper.valueToTree(updateUserDTO);

        createUserDTOObject.fields().forEachRemaining( entry -> {
            if (
                    !entry.getValue().isNull() &&
                    (userObject.get(entry.getKey()) != entry.getValue() && !Objects.equals(entry.getKey(), "email"))
            ){
                System.out.println(entry.getKey() + " " + entry.getValue());
                userObject.set(entry.getKey(), entry.getValue());
            }
        });

        List<Role> roles = objectMapper.convertValue(userObject.get("roles"), new TypeReference<List<Role>>(){});
        User updatedUser = User.builder()
                        .id(userObject.get("id").longValue())
                        .name(userObject.get("name").asText())
                        .password(userObject.get("password").asText())
                        .email(userObject.get("email").asText())
                        .cnpj(userObject.get("cnpj").asText())
                        .roles(roles)
                        .build();

        userRepository.save(updatedUser);

    }

    public void updatePassword(UpdatePasswordDTO updatePasswordDTO, HttpServletRequest request) throws RuntimeException{
        String email = userAuthenticationFilter.getEmailToken(request);

        if(
                !Objects.equals(updatePasswordDTO.password(), updatePasswordDTO.confirmPassword())
        ) throw new RuntimeException("Passwords are diferents");

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        String encodedPassword = passwordEncoder.encode(updatePasswordDTO.password());

        User updatePassword = User.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .password(encodedPassword)
                        .email(user.getEmail())
                        .cnpj(user.getCnpj())
                        .roles(user.getRoles())
                        .build();

        userRepository.save(updatePassword);
    }

}
