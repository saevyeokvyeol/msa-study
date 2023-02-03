package com.msa.example.chapter03.scope;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class SpringBeanApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBeanApplication.class, args);

        // 스프링 빈 스코프인 singleton과 prototype의 차이 확인을 위한 멀티 스레드 준비
        ThreadPoolTaskExecutor taskExecutor = applicationContext.getBean(ThreadPoolTaskExecutor.class);

        final String dateString = "2023-02-02T23:59:59.-08:00";

        // 멀티 스레드 환경으로 taskExecutor.submit() 메소드 100번 실행
        for (int i = 0; i < 100; i++) {
            taskExecutor.submit(() -> {
                try {
                    DateFormatter formatter = applicationContext.getBean("singletonDateFormatter", DateFormatter.class);
                    log.info("Date : {}, hashCode : {}", formatter.parse(dateString), formatter.hashCode());
                } catch (Exception e) {
                    log.error("error to parse", e);
                }
            });
        }
        TimeUnit.SECONDS.sleep(5);
        applicationContext.close();
    }

    /*
    * @Scope("prototype"): 기본값인 singleton 대신 prototype으로 설정
    * 스프링 빈 객체를 단 하나만 생성해 매번 똑같은 것으로 주입하는 대신 주입할 때마다 새 스프링 빈 객체를 생성
    * DateFormatter 멤버 필드인 SimpleDateFormat가 멀티 스레드 환경에서 제대로 동작하지 않기 때문에 멀티 스레드 환경에서 오류를 없애기 위함
    * */
    @Bean
    @Scope("prototype")
    public DateFormatter singletonDateFormatter() {
        return new DateFormatter("yyyy-MM-dd'T'HH:mm:ss");
    }
}