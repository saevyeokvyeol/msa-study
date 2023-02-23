package com.msa.example.chapter07.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
// 관점 클래스를 스프링 빈으로 설정
@Component
// 관점 클래스 정의
@Aspect
// 다른 관점 클래스와의 우선 순위 설정
@Order(2)
public class ElapseLoggingAspect {
    /*
    * 기존 execution 포인트 컷 표현식 대신 @annotation 포인트 컷 표현식 사용
    * : ElapseLoggable 어노테이션이 적용된 모든 메소드가 포인트 컷 대상
    * */
    @Around("@annotation(ElapseLoggable)")
    //                            Around 어드바이스 타입 -> 대상 객체 메소드 실행 가능한 ProceedingJoinPoint 주입
    public Object printElapseTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("start time clock");

        Object result;

        try {
            /*
            * ProceedingJoinPoint 객체의 proceed() 메소드 실행 시 대상 클래스의 메소드 실행
            * 대상 클래스에서 리턴되는 값이 있을 경우 반드시 리턴해야 하기 때문에 리턴값 저장
            * */
            result = proceedingJoinPoint.proceed();
        } finally {
            stopWatch.stop();
            String methodName = proceedingJoinPoint.getSignature().getName();
            long elasedTime = stopWatch.getLastTaskTimeMillis();
            log.info("{}, elapsed time: {} ms", methodName, elasedTime);
        }

        // 대상 클래스
        return result;
    }
}
