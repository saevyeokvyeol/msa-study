package com.msa.example.chapter03.samePackageBean;

import com.msa.example.chapter03.annotation.Formatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

@Slf4j
@SpringBootApplication
public class SpringBeanApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringBeanApplication.class);

        Formatter formatter = applicationContext.getBean("dateFormatter", Formatter.class);
        String dateString = formatter.of(new Date());
        log.info("Date : {}", dateString);

        applicationContext.close();
    }
}