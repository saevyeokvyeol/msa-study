package com.msa.example.chapter07.aspect;

import com.msa.example.chapter07.controller.HotelRequest;
import com.msa.example.chapter07.controller.HotelResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
// 관점 클래스 선언
@Aspect
// ArgumentLoggingAspect를 스프링 빈으로 정의
@Component
@Order(1)
public class ArgumentLoggingAspect {
    /*
    * @Before 어노테이션을 이용해 Before 어드바이스 사용
    * 포인트 컷은 HotelRequest 인자를 받는 모든 메소드
    * -> 경로에는 와일드 카드, 파라미터에는 HotelResquest 클래스 입력
    * */
    @Before("execution(* *(com.msa.example.chapter07.controller.HotelRequest, ..))")
    public void printHotelRequestArgument(JoinPoint joinPoint) { // JoinPoint를 주입받기 위해 파라미터에 작성
        //                                   joinPorint의 파라미터를 배열로 응답
        String argumentValue = Arrays.stream(joinPoint.getArgs())
            // HotelRequest.class를 이용해 같은 클래스 타입인 객체만 필터링
            .filter(obj -> HotelRequest.class.equals(obj.getClass()))
            .findFirst()
            .map(HotelRequest.class::cast)
            .map(hotelRequest -> hotelRequest.toString())
            .orElseThrow();

        log.info("argument info : {}", argumentValue);
    }
}
