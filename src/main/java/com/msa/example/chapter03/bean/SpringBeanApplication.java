package com.msa.example.chapter03.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Locale;

@Slf4j
@SpringBootApplication
public class SpringBeanApplication {

    public static void main(String[] args) {
        /*
        * SpringApplication.run()이 리턴하는 ApplicationContext 객체를 ctxt에 저장
        *
        * ApplicationContext == 스프링 빈 컨테이너
        * 파라미터인 SpringBeanApplication 클래스를 설정 파일로 로딩
        * */
        ConfigurableApplicationContext ctxt = SpringApplication.run(SpringBeanApplication.class, args);

        /*
        * ApplicationContext.getBean()이 리턴하는 스프링 빈 객체를 defaultPriceUnit에 저장
        * 이 때, 스프링 빈 컨테이너는 파라미터를 이용해 이름이 "priceUnit"이고 타입이 PriceUnit.class인 스프링 빈 객체를 리턴함
        * */
        PriceUnit defaultPriceUnit = ctxt.getBean("priceUnit", PriceUnit.class);
        log.info("Price #1 : {}", defaultPriceUnit.format(BigDecimal.valueOf(10.2)));

        PriceUnit wonPriceUnit = ctxt.getBean("wonPriceUnit", PriceUnit.class);
        log.info("Price #2 : {}", wonPriceUnit.format(BigDecimal.valueOf(1000)));

        ctxt.close();
    }

    @Bean(name = "priceUnit") // 스프링 빈 이름 설정(생략 시 메소드 이름 == 스프링 빈 이름)
    public PriceUnit dollarPriceUnit() { // 리턴 타입인 PriceUnit == 스프링 빈 클래스 타입
        return new PriceUnit(Locale.US); // 리턴 객체 == 스프링 빈 클래스
    }

    @Bean
    public PriceUnit wonPriceUnit() {
        return new PriceUnit(Locale.KOREA);
    }
}
