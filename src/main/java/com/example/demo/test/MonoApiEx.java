package com.example.demo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mono/ex")
public class MonoApiEx {

    /**
     * 데이터를 내보내는 mono 객체 생성
     *
     * @return
     */
    @GetMapping("/1")
    public Mono<String> ex1(){
        Mono<String> stringMono = Mono.just("Hello Mono ~ ");

        return stringMono;
    }
}
