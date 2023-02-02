package com.msa.example.chapter02.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
/*
* postProcessBeforeInitialization()와 postProcessAfterInitialization() 메소드를 오버라이딩하기 위해 BeanPostProcessor 상속
* postProcessBeforeInitialization()은 스프링 빈이 초기화되기 전, postProcessAfterInitialization()은 스프링 빈이 초기화된 후 실행됨
* */
public class PrintableBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("lifeCycleComponent".equals(beanName)) log.error("Called postProcessBeforeInitialization() for : {}", beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ("lifeCycleComponent".equals(beanName)) log.error("Called postProcessBeforeInitialization() for : {}", beanName);
        return bean;
    }
}
