package com.marley_store.stock_system.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    public String helloWorld(String name){
        return "Hello, " + name;
    }
}
