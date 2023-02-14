package com.msa.example.chapter06.controller.converter;

import com.msa.example.chapter06.domain.HotelRoomNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/*
 * roomNumber를 HotelRoomNumber로 변환하는 Converter 구현체
 * 변환 대상인 roomNumber는 문자열이고 HotelRoomNumber 타입으로 변환해야 하기 때문에 구현할 Converter의 제네릭 타입은 String과 HotelRoomNumber
 * convert() 메소드의 source 타입 역시 변환 대상인 roomNumber의 타입을 따라 String
 * */
@Component
public class HotelRoomNumberConverter implements Converter<String, HotelRoomNumber> {
    @Override
    public HotelRoomNumber convert(String source) {
        return HotelRoomNumber.parse(source);
    }
}
