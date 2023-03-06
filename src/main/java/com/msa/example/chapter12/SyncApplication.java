package com.msa.example.chapter12;

import com.msa.example.chapter12.event.server.ApplicationEventListener;
import com.msa.example.chapter12.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SyncApplication {
    public static void main(String[] args) {
        // SpringApplication와 ApplicationContext를 설정할 수 있는 SpringApplicationBuilder 객체 생성
        SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(SyncApplication.class);

        // SpringApplicationBuilder 객체를 이용해 SpringApplication 생성
        SpringApplication application = appBuilder.build();

        /*
        * SpringApplication의 addListeners() 메소드는 하나 이상의 ApplicationListener 객체를 받음
        * ApplicationEventListener() 객체를 생성해 인자로 전달하면 ApplicationReadyEvent 이벤트 구독 가능
        * */
        application.addListeners(new ApplicationEventListener());

        ConfigurableApplicationContext ctxt = application.run(args); // 실행

        UserService userService = ctxt.getBean(UserService.class);
        userService.createUser("YUDA GIM", "test.com");
    }
}
