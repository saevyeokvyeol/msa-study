package com.msa.example.chapter07.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/*
 * src/main/java/com/msa/example/chapter07/Thread/ThreadPoolConfig.java와 비교
 * @TestConfiguration
 * : 테스트 실행 시 내부에 정의된 스프링 빈 생성
 *   src/main/java 경로 안에서 정의된 스프링 빈과 이름, 타입이 같다면 재정의
 * */
@TestConfiguration
public class TestConfig {

    /*
    * 스프링 빈 이름은 threadPoolTaskExecutor, 타입은 ThreadPoolTaskExecutor
    * src/main/java 경로 안에서 정의된 스프링 빈과 이름, 타입이 같으므로 재정의
     * */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(1); // 최대 스레드 갯수
        taskExecutor.setThreadNamePrefix("TestExecutor-"); // 스레드 이름 접두사
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
