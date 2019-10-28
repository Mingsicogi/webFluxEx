package com.example.demo.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/flux/ex")
public class FluxAPIEx {

    /**
     * 데이터를 내보내는 flux 객체 생성
     *
     * @return
     */
    @GetMapping("/1")
    public Flux<String> ex1(){
        Flux<String> stringFlux = Flux.just("Hello Flux ~ ", " HI Flux ~~");

        return stringFlux;
    }
}
