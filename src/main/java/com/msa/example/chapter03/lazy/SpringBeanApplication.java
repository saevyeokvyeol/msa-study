package com.msa.example.chapter03.lazy;

import com.msa.example.chapter03.bean.PriceUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.Locale;

@Slf4j
@SpringBootApplication
public class SpringBeanApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt = SpringApplication.run(SpringBeanApplication.class);
        log.info("---------- Done to initialize spring beans");
        PriceUnit priceUnit = ctxt.getBean("lazyPriceUnit", PriceUnit.class);
        log.info("Locale in priceUnit : {}", priceUnit.getLocale().toString());
        ctxt.close();
    }

    @Bean
    @Lazy // @Lazy 어노테이션을 사용하면 의존성 주입 시 스프링 빈이 초기화됨
    public PriceUnit lazyPriceUnit() {
        log.info("initialize lazyPriceUnit");
        return new PriceUnit(Locale.US);
    }
}