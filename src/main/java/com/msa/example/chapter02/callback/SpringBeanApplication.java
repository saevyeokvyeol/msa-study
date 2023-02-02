package com.msa.example.chapter02.callback;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

public class SpringBeanApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt = SpringApplication.run(SpringBeanApplication.class);
        ctxt.close();
    }

    /*
    * LifeCycleComponent 클래스의 init 메소드와 destroy 메소드 이름을 설정
    * LifeCycleComponent 스프링 빈이 생성되었을 때와 종료될 때 설정한 이름과 동일한 메소드가 실행됨
    * */
    @Bean(initMethod = "init", destroyMethod = "clear")
    public LifeCycleComponent lifeCycleComponent() {
        return new LifeCycleComponent();
    }

    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new PrintableBeanPostProcessor();
    }
}
