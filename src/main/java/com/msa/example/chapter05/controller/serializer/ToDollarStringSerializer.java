package com.msa.example.chapter05.controller.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/*
* json으로 마셜링할 때,
* jackson 라이브러리에서 기본으로 제공하는 클래스 외에 다른 클래스로 변환하고 싶을 때 JsonSerializer를 상속받아 새 클래스 생성
* 이 때, 변환할 대상의 클래스 타입을 제네릭 타입으로 설정
* */
public class ToDollarStringSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.setScale(2).toString());
    }
}