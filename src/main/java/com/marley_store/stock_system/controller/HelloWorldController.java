package com.marley_store.stock_system.controller;

import com.marley_store.stock_system.model.User;
import com.marley_store.stock_system.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Stateless -> A cada nova requisilçao eu recebo todas as informações que eu orecusi oara fazer aquela
 * funcionalidade que o cliente solicita
 * Statefull -> O estado de cada cliente é mantido no servidor
 */
@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    //Injeção de dependencia - há a injeção do HelloWorldService em HelloWolrdController
    // pode ser substituido pelo decorator @Autowired
    //    public HelloWorldController(HelloWorldService helloWorldService){
    //        this.helloWorldService = helloWorldService;
    //    }

    @GetMapping()
    public String helloWorld(){
        return helloWorldService.helloWorld("Lucas");
    }

    // exemplo de post com body
//    @PostMapping
//    public String helloWorldPost(@RequestParam(value="filter") String filter, @RequestBody User body){
//        return "Hello world post, name: " + body.getName() + "\nEmail: " + body.getEmail() + "\nparam: " + filter;
//    }

    @PostMapping("/{id}")
    public String helloWorldPostWithVariables(@PathVariable("id") String id){
        return "Hello world post com parametro: " + id;
    }
}
