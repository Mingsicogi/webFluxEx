package com.example.demo.test;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class AsyncEx {



    AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

    String url = "http://localhost:18083/service1?req={req}";
    String url2 = "http://localhost:18083/service2?req={req}";

    @GetMapping("/async")
    public DeferredResult<String> async(Integer idx) throws Exception {
        DeferredResult<String> dr = new DeferredResult<>();

        toCF(rt.getForEntity(url, String.class, "h" + idx))
                .thenCompose(s -> toCF(rt.getForEntity(url2, String.class, s.getBody())))
//                .thenAcceptAsync(s -> myService.work(s.getBody()))
                .thenAccept(s -> dr.setResult(String.valueOf(s)))
                .exceptionally(e -> {
                    dr.setErrorResult(e.getMessage());
                    return (Void)null;
                });

        return dr;
    }

    private <T> CompletableFuture<T> toCF(ListenableFuture<T> lf){
        CompletableFuture<T> cf = new CompletableFuture<>();
        lf.addCallback(s -> cf.complete(s), e -> cf.completeExceptionally(e));

        return cf;
    }

//    @Service("MyService")
    public static class MyService{
        public String work(String req){
            return req + "/asyncwork";
        }
    }
}
