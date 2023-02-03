package com.msa.example.chapter03.primary;

import com.msa.example.chapter03.bean.PriceUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Locale;

@Slf4j
@SpringBootApplication
public class SpringBeanApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt = SpringApplication.run(SpringBeanApplication.class);
        PriceUnit priceUnit = ctxt.getBean(PriceUnit.class);
        log.info("Locale in PriceUnit : {}", priceUnit.getLocale().toString());
        ctxt.close();
    }

    /*
    * 타입이 같은 스프링 빈이 여러 개 있을 때, 타입에 의한 주입을 사용하면 예외가 발생함
    * 이 때 최우선으로 주입할 스프링 빈에 @Primary 어노테이션을 사용하면 해당 스프링 빈이 우선 주입됨
    * */
    @Bean
    @Primary
    public PriceUnit primaryPriceUnit() {
        return new PriceUnit(Locale.US);
    }

    @Bean
    public PriceUnit secondaryPriceUnit() {
        return new PriceUnit(Locale.KOREA);
    }
}
