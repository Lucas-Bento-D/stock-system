package com.marley_store.stock_system.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marley_store.stock_system.dto.restMessage.RestDataMessageDTO;
import com.marley_store.stock_system.dto.user.*;
import com.marley_store.stock_system.dto.jwtToken.RecoveryJwtTokenDTO;
import com.marley_store.stock_system.dto.restMessage.RestMessageDTO;
import com.marley_store.stock_system.model.user.*;
import com.marley_store.stock_system.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<RestMessageDTO> createUser(@RequestBody CreateUserDTO createUserDTO){
        userService.createUser(createUserDTO);
        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.CREATED.value(), "User updated successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @GetMapping("/get")
    public ResponseEntity<RestDataMessageDTO<GetUserDTO>> getUser(HttpServletRequest request){
        GetUserDTO user = userService.findByEmail(request);
        RestDataMessageDTO<GetUserDTO> restUserDTO = new RestDataMessageDTO<>(HttpStatus.OK.value(), "User request succesfully", user);
        return ResponseEntity.status(HttpStatus.OK).body(restUserDTO);
    }

    @PatchMapping("/update")
    public ResponseEntity<RestMessageDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO, HttpServletRequest request) throws JsonProcessingException {
        userService.updateUser(updateUserDTO, request);
        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.OK.value(), "User updated successfully!");
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<RestMessageDTO> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, HttpServletRequest request){
        userService.updatePassword(updatePasswordDTO, request);
        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.OK.value(), "User password updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<RestMessageDTO> deleteUser(HttpServletRequest request){
        userService.deleteUser(request);
        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.OK.value(), "User password deleted successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<RestDataMessageDTO<RecoveryJwtTokenDTO>> authenticateUser(@RequestBody LoginUserDTO loginUserDto){
        RecoveryJwtTokenDTO token = userService.authenticateUser(loginUserDto);
        RestDataMessageDTO<RecoveryJwtTokenDTO> successMessage = new RestDataMessageDTO(HttpStatus.OK.value(), "User logged succesfully", token);
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

    @GetMapping("/test")
    public ResponseEntity<RestMessageDTO> getAuthenticationTest() {
        RestMessageDTO successMessage = new RestMessageDTO(HttpStatus.OK.value(), "User authenticated");
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }
}
