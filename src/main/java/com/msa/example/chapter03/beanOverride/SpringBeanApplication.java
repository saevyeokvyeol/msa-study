package com.msa.example.chapter03.beanOverride;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@SpringBootApplication
public class SpringBeanApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt = SpringApplication.run(SpringBeanApplication.class);
        Object obj = ctxt.getBean("systemId");
        log.warn("Bean Info. type:{}, value:{}", obj.getClass(), obj);
        ctxt.close();
    }

    /*
    * 이름이 같은 스프링 빈이 있을 경우 에러 발생
    * application.properties에 spring.main.allow-bean-definition-overriding=true 설정을 추가할 경우
    * 이름이 같은 스프링 빈이 있어도 에러가 발생하지 않고 덮어씀
    * */
    @Configuration
    class SystemConfig01 {
        @Bean
        public Long systemId() {
            return 1111L;
        }
    }

    @Configuration
    class SystemConfig02 {
        @Bean
        public String systemId() {
            return new String("OrderSystem");
        }
    }
}
