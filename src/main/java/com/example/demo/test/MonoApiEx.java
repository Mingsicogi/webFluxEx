package com.example.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/mono/ex")
@Slf4j
public class MonoApiEx {

    /**
     * 데이터를 내보내는 mono 객체 생성 1
     *
     * @return
     */
    @GetMapping("/1")
    public Mono<String> ex1(){
        Mono<String> stringMono = Mono.just("Hello Mono ~ ");

        return stringMono.log();
    }

    /**
     * 데이터를 내보내는 mono 객체 생성 2
     *
     * @return
     */
    @GetMapping("/2")
    public Mono<String> ex2(){
        List<String> strList = Arrays.asList("a","ab","abc","bc");
        Mono<String> emptyMono = Mono.justOrEmpty(strList.stream().filter(str -> str.startsWith("d")).findFirst());

        return emptyMono.log();
    }

    /**
     * Map operation
     *
     * @return
     */
    @GetMapping("/3")
    public Mono<List<Integer>> ex3(){
        List<Integer> strList = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).map(i -> i * 2).collect(toList());

        return Mono.just(strList).log();
    }
}
