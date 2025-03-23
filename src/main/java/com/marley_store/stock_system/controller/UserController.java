package com.marley_store.stock_system.controller;

import com.marley_store.stock_system.model.user.StatusUser;
import com.marley_store.stock_system.model.user.User;
import com.marley_store.stock_system.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create-user")
    public StatusUser createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/delete-user")
    public StatusUser deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }
}
