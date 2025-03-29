package com.marley_store.stock_system.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marley_store.stock_system.config.security.SecurityConfiguration;
import com.marley_store.stock_system.dto.user.CreateUserDTO;
import com.marley_store.stock_system.dto.user.LoginUserDTO;
import com.marley_store.stock_system.dto.jwtToken.RecoveryJwtTokenDTO;
import com.marley_store.stock_system.model.role.Role;
import com.marley_store.stock_system.model.role.RoleName;
import com.marley_store.stock_system.model.user.*;
import com.marley_store.stock_system.model.user.userDetailsImpl.UserDetailsImpl;
import com.marley_store.stock_system.repository.UserRepository;
import com.marley_store.stock_system.service.jwtToken.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    public RecoveryJwtTokenDTO authenticateUser(LoginUserDTO loginUserDto){
        System.out.println("Chamando authenticateUser com: " + loginUserDto.email());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
        System.out.println(usernamePasswordAuthenticationToken);

        System.out.println("Autenticação bem-sucedida para: " + loginUserDto.email());


        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    public void updateUser(CreateUserDTO createUserDTO) throws JsonProcessingException {
        User user = userRepository.findByEmail(createUserDTO.email()).orElseThrow(() -> new RuntimeException("Usuario n'ao encontrado"));
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectNode userObject = objectMapper.valueToTree(user);
        ObjectNode createUserDTOObject = objectMapper.valueToTree(createUserDTO);

//        ObjectNode objectMeged = objectMapper.createObjectNode();
//        objectMeged.setAll((ObjectNode) createUserDTOObject);
//        objectMeged.setAll((ObjectNode) userObject);

        createUserDTOObject.fields().forEachRemaining( entry -> {
            if (
                    !entry.getValue().isNull() &&
                    (userObject.get(entry.getKey()) != entry.getValue() && !Objects.equals(entry.getKey(), "email"))
            ){
                System.out.println(entry.getKey() + " " + entry.getValue());
                userObject.set(entry.getKey(), entry.getValue());
            }
        });

//        CreateUserDTO newUser = objectMapper.treeToValue(userObject, CreateUserDTO.class);
        List<Role> roles = List.of(Role.builder().name(RoleName.valueOf(userObject.get("roles").asText())).build());
        User updatedUser = User.builder()
                        .id(userObject.get("id").longValue())
                        .name(userObject.get("name").asText())
                        .password(userObject.get("password").asText())
                        .email(userObject.get("email").asText())
                        .cnpj(userObject.get("cnpj").asText())
                        .roles(roles).build();
        System.out.println(updatedUser.toString());
//        userRepository.save(updatedUser);

    }

}
