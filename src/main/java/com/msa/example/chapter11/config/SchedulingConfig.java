package com.msa.example.chapter11.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

// @EnableScheduling: 스케줄링을 위한 어노테이션
@EnableScheduling
@Configuration
public class SchedulingConfig implements SchedulingConfigurer { // 스케줄링 설정을 위해 SchedulingConfigurer 구현

    // configureTasks() 파라미터로 ScheduledTaskRegistrar 구현체 설정
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 두 개 이상의 태스크를 동시에 실행해야 하므로 ThreadPoolTaskScheduler 구현 클래스 사용
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // ThreadPoolTaskScheduler 스레드 갯수 설정
        taskScheduler.setPoolSize(10);
        // ThreadPoolTaskScheduler 스레드 이름의 말머리 설정
        taskScheduler.setThreadNamePrefix("TaskScheduler-");
        // ThreadPoolTaskScheduler 객체 초기화
        taskScheduler.initialize();

        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}
