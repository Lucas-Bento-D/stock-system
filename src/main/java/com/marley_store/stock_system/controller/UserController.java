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

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getUser(@RequestParam(value="byName") String name){
        return userService.findByName(name);
    }

    @GetMapping("/get-all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/delete")
    public StatusUser deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDTO createUserDTO){
        userService.createUser(createUserDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> authenticateUser(@RequestBody LoginUserDTO loginUserDto){
        RecoveryJwtTokenDTO token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PatchMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody  CreateUserDTO createUserDTO, HttpServletRequest request) throws JsonProcessingException {
        userService.updateUser(createUserDTO, request);
        return new ResponseEntity<>("Usuario atualizado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }
}
