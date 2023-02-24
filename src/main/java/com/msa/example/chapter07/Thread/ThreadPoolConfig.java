package com.msa.example.chapter07.Thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*
* src/test/java/com/msa/example/chapter07/config/TestConfig.java와 비교
* @Configuration
* : 어플리케이션이 실행될 때 내부에 정의된 스프링 빈이 생성
* */
@Configuration
public class ThreadPoolConfig {
    
    // 스프링 빈 이름은 threadPoolTaskExecutor, 타입은 ThreadPoolTaskExecutor
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10); // 최대 스레드 갯수
        taskExecutor.setThreadNamePrefix("AsyncExecutor-"); // 스레드 이름 접두사
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
