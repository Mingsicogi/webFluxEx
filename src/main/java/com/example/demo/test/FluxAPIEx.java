package com.example.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/flux/ex")
@Slf4j
public class FluxAPIEx {

    /**
     * 데이터를 내보내는 flux 객체 생성
     *
     * @return
     */
    @GetMapping("/1")
    public Flux<Integer> ex1(){
        Flux<Integer> stringFlux = Flux.just(1,2,3,4,5,6,7,8,9,10);

        return stringFlux;
    }

    /**
     * 데이터를 내보내는 flux 객체 생성
     *
     * @return
     */
    @GetMapping("/2")
    public Flux<String> ex2(){
        Flux.just(1,2,3,4,5,6,7,8,9,10).log().subscribe(new Subscriber<>() {
            private Subscription s = null;
            private int count;

            @Override
            public void onSubscribe(Subscription subscription) {
                s = subscription;
                subscription.request(2);
            }

            /**
             * back pressure - subscriber의 요청에 맞게 데이터를 받
             *
             * @param integer
             */
            @Override
            public void onNext(Integer integer) {
                log.info("### {}", integer);
                if(count++ % 2 == 0){
                    s.request(2);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });

        return Flux.just("OK");
    }

    /**
     * Map operation
     *
     * @return
     */
    @GetMapping("/3")
    public Flux<Integer> ex3(){
        Flux<Integer> stringFlux = Flux.just(1,2,3,4,5,6,7,8,9,10);

        return stringFlux.log().map(num -> num * 2);
    }

    /**
     * Combine two stream
     *
     * @return
     */
    @GetMapping("/4")
    public Flux<Integer> ex4(){
        return Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .map(i -> i * 2)
                .zipWith(Flux.just(11, 12, 13, 14, 15, 16, 17, 18, 19, 20), (a, b) -> {
                    log.info("{} | {}", a, b);
                    return a + b;
                });
    }
}
