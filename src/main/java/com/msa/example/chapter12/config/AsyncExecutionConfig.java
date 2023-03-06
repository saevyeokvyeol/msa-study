package com.msa.example.chapter12.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
// @EnableAsync: 어플리케이션에서 @Async 어노테이션을 사용하기 위해 설정
@EnableAsync
// @Async 어노테이션과 스레드 풀 스레드를 사용하기 위해 AsyncConfigurer 인터페이스 구현
public class AsyncExecutionConfig implements AsyncConfigurer {

    // 프레임워크가 스레드 풀을 설정할 때 사용하는 콜백 메소드
    @Override
    public Executor getAsyncExecutor() {
        return getExecutor();
    }

    private Executor getExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setThreadNamePrefix("asyncExecutor-");
        return threadPoolTaskExecutor;
    }
}
