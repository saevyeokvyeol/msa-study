package com.msa.example.chapter05.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HotelRoomType {
    /*
    * HotelRoomType.SINGLE의 값 = String 타입 문자열 "single"
    * */
    SINGLE("single"),
    DOUBLE("double"),
    TRIPLE("triple"),
    QUAD("quad");

    private static final Map<String, HotelRoomType> paramMap = Arrays.stream(HotelRoomType.values())
        .collect(Collectors.toMap(
            HotelRoomType::getParam,
            Function.identity()
        ));

    /*
    * enum 클래스 내부에 String param 변수 선언
    * param 변수는 마셜링 후 사용되는 JSON 객체 값을 저장함
    * */
    private final String param;

    /*
    * String param 파라미터를 받아 json 객체 값으로 사용될 값을 인수로 인력
    * Ex. SINGLE 상수는 문자열 'single'이 할당됨
    * */
    HotelRoomType(String param) {
        this.param = param;
    }

    /*
    * @JsonCreator
    * : 언마셜링 과정에서 값 변환에 사용되는 메소드를 지정하는 어노테이션
    * */
    @JsonCreator
    public static HotelRoomType fromParam(String param) {
        return Optional.ofNullable(param)
            .map(paramMap::get)
            .orElseThrow(() -> new IllegalArgumentException("param is not valid"));
    }

    /*
    * @JsonValue
    * : 마셜링 과정에서 값 변환에 사용되는 메소드를 지정하는 어노테이션
    * */
    @JsonValue
    public String getParam() {
        return this.param;
    }
}
