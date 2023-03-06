package com.msa.example.chapter12.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncEventConfig {
    @Bean(name="applicationEventMulticaster") // 반드시 AbstractApplicationContext에서 지정한 스프링 빈 이름으로 설정해야 함
    public ApplicationEventMulticaster applicationEventMulticaster(TaskExecutor asyncEventTaskExecutor) {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        // 주입받은 asyncEventTaskExecutor을 eventMulticaster의 setTaskExecutor() 메소드 파라미터로 입력해 설정에 사용
        eventMulticaster.setTaskExecutor(asyncEventTaskExecutor);
        return eventMulticaster;
    }

    @Bean
    public TaskExecutor asyncEventTaskExecutor() {
        ThreadPoolTaskExecutor asyncEventTaskExecutor = new ThreadPoolTaskExecutor();
        asyncEventTaskExecutor.setMaxPoolSize(10);
        asyncEventTaskExecutor.setThreadNamePrefix("eventExecutor-");
        asyncEventTaskExecutor.afterPropertiesSet();
        return asyncEventTaskExecutor;
    }
}
