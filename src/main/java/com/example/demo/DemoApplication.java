package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {
    "classpath:mongo.properties"
}, encoding = "UTF-8")
@SpringBootApplication
public class DemoApplication {

    /**
     * https://projectreactor.io/docs/netty/release/api/constant-values.html
     * - 설정 참고
     *
     * @param args
     */
    public static void main(String[] args) {
        System.setProperty("reactor.netty.ioWorkerCount", "1");
        System.setProperty("reactor.netty.pool.maxConnections", "1");
        SpringApplication.run(DemoApplication.class, args);

    }
}
