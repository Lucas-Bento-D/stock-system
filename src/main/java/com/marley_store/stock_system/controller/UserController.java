package com.marley_store.stock_system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marley_store.stock_system.dto.user.CreateUserDTO;
import com.marley_store.stock_system.dto.user.LoginUserDTO;
import com.marley_store.stock_system.dto.jwtToken.RecoveryJwtTokenDTO;
import com.marley_store.stock_system.model.user.*;
import com.marley_store.stock_system.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO){
        userService.createUser(createUserDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @RequestParam(value="byName", required=false) String name
    @GetMapping("/get")
    public Optional<User> getUser(HttpServletRequest request){
//        return name.isEmpty() ? userService.getAllUsers() : userService.findByName(name);
        return userService.findByEmail(request);
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody  CreateUserDTO createUserDTO, HttpServletRequest request) throws JsonProcessingException {
        userService.updateUser(createUserDTO, request);
        return new ResponseEntity<>("Usuario atualizado com sucesso!", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public StatusUser deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> authenticateUser(@RequestBody LoginUserDTO loginUserDto){
        RecoveryJwtTokenDTO token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }
}
