package com.msa.example.chapter10.service;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class HotelKeyGenerator implements KeyGenerator {

    private final String PREFIX = "HOTEL::";

    // 캐시 키를 생성할 때 KeyGenerator 구현체가 설정되어 있으면 generate() 메소드를 사용해 키 생성
    @Override
    public Object generate(Object target, Method method, Object... params) {

        if (Objects.isNull(params))
            return "NULL";

        return Arrays.stream(params)
            // 인자 중에서 HotelRequest 타입 검색
            .filter(param -> param instanceof HotelRequest)
            .findFirst()
            // HotelRequest 타입 파라미터가 있을 경우 'HOTEL:: + hotelId'로 키 생성
            .map(obj -> HotelRequest.class.cast(obj))
            .map(hotelRequest -> PREFIX + hotelRequest.getHotelId())
            // HotelRequest 타입 파라미터가 없을 경우 SimpleKeyGenerator으로 캐시 키 생성
            .orElse(SimpleKeyGenerator.generateKey(params).toString());
    }
}