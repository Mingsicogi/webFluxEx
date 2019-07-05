package com.example.demo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Welcome {

    @GetMapping("/hello/webFlux")
    public Mono<String> welcome(String message){


        return Mono.just("welcome");
    }
}
