package com.marley_store.stock_system.service;

import com.marley_store.stock_system.model.StatusUser;
import com.marley_store.stock_system.model.User;
import com.marley_store.stock_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findByName(String name){
        return userRepository.findByName(name);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public StatusUser createUser(User user){
        User userr = new User(user);
        String teste = userr.getEmail();
        boolean teste2 = userRepository.existsByEmail(userr.getEmail());

        if( userRepository.existsByEmail(userr.getEmail()) ){
            return new StatusUser(409, "User already exist");
        }
            try{
                userRepository.saveAndFlush(userr);
                return new StatusUser(201, "User Created");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    public StatusUser deleteUser(User user){
        List<User> users = userRepository.findByEmail(user.getEmail());
        List<Long> usersId = users.stream().map(user1 -> user1.getId()).collect(Collectors.toList());
        userRepository.deleteAllById(usersId);
        return new StatusUser(204, "User Deleted");
    }


}
