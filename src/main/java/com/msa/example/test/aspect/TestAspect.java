package com.msa.example.test.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TestAspect {
    @Before("execution(* testMethod())")
    public void beforeAspect() {
        System.out.println("beforeAspect 메소드 호출");
    }
}
