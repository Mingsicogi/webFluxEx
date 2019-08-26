package com.example.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@RestController
@EnableAsync
@Slf4j
public class Welcome {

    @Autowired
    private MyService myService;

    String url1 = "http://localhost:18083/service1?req={req}";
    String url2 = "http://localhost:18083/service2?req={req}";

    WebClient client = WebClient.create();

    @GetMapping("/hello/webFlux")
    public Mono<String> welcome(Integer idx){
        return client.get().uri(url1, idx).exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .doOnNext(c -> log.info("{}", c))
                .flatMap(response -> client.get().uri(url2, response).exchange())
                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class))
                .doOnNext(c -> log.info("{}", c))
                .flatMap(res -> Mono.fromCompletionStage(myService.work(res)))
                .doOnNext(c -> log.info("{}", c));
    }

    @Service("myService")
    public class MyService{

        @Async
        public CompletableFuture<String> work(String req){
            return CompletableFuture.completedFuture(req + "_asyncwork");
        }
    }
}
