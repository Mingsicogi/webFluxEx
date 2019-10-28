package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("minsAsync")
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(10);//thread pool size
        threadPoolTaskExecutor.setMaxPoolSize(20); // 큐가 가득 찼을때 늘어나는 max 사이즈
        threadPoolTaskExecutor.setQueueCapacity(10);// 큐에 대기하는 스레트 갯수
        threadPoolTaskExecutor.setThreadNamePrefix("MinsThread - ");
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }
}
