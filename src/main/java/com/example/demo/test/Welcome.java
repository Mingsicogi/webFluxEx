package com.example.demo.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@EnableAsync
@Slf4j
public class Welcome {

    @Autowired
    private MyService myService;

    private String url1 = "http://localhost:18083/service1?req={req}";
    private String url2 = "http://localhost:18083/service2?req={req}";

    private WebClient client = WebClient.create();

    @GetMapping("/ip")
    public ResponseEntity<String> getMyIp(){
        String systemipaddress = "";

        try {

            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
            systemipaddress = sc.readLine().trim();
            if (!(systemipaddress.length() > 0))
            {
                try
                {
                    InetAddress localhost = InetAddress.getLocalHost();
                    log.info((localhost.getHostAddress()).trim());
                    systemipaddress = (localhost.getHostAddress()).trim();
                }
                catch(Exception e1)
                {
                    systemipaddress = "Cannot Execute Properly";
                }
            }
        }
        catch (Exception e2) {
            systemipaddress = "Cannot Execute Properly";
        }

        log.info("\nYour IP Address: " + systemipaddress +"\n");

        return new ResponseEntity<>(systemipaddress, HttpStatus.OK);
    }

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

    @GetMapping("/mono")
    public Mono<List<Event>> hello(){// Publisher -> (publisher) -> (publisher) ... -> Subscriber
	    List<Event> returnData = new ArrayList<>();
	    //test123
	    //test2222222

	    returnData.add(new Event("aaa", "0101234566778", "길동홍"));
	    returnData.add(new Event("bbb", "01012938712373", "영수킴"));

        return Mono.just(returnData).log();
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Event> fluxEx(){

//	    List<Event> returnData = new ArrayList<>();
//
//	    returnData.add(new Event("aaa", "0101234566778", "길동홍"));
//	    returnData.add(new Event("bbb", "01012938712373", "영수킴"));

	    Stream<Event> generateDataList = Stream.generate(() -> new Event(String.valueOf(System.currentTimeMillis()), UUID.randomUUID().toString(), "test"))/*.limit(10)*/;

    	return Flux.fromStream(generateDataList).take(10);
    }

    @Service("myService")
    public class MyService{

        @Async
        public CompletableFuture<String> work(String req){
            return CompletableFuture.completedFuture(req + "_asyncwork");
        }
    }

    @Data
    @AllArgsConstructor
    private static class Event{
    	private String userId;
    	private String phone;
    	private String userName;
    }
}
