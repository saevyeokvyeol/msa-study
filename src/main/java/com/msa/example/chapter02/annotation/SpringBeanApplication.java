package com.msa.example.chapter02.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
public class SpringBeanApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt = SpringApplication.run(SpringBeanApplication.class);

        Formatter<LocalDateTime> formatter = ctxt.getBean("localDateTimeFormatter", Formatter.class);
        String date = formatter.of(LocalDateTime.of(2023, 1, 29, 15, 20, 33));

        log.info("Date : " + date);
        ctxt.close();
    }
}
