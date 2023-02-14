package com.msa.example.chapter06.controller.formatter;

import com.msa.example.chapter06.domain.HotelRoomNumber;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class HotelRoomNumberFormatter implements Formatter<HotelRoomNumber> {
    /*
    * String 문자열을 도메인 모델로 변경하는 역할
    * */
    @Override
    public HotelRoomNumber parse(String text, Locale locale) throws ParseException {
        return HotelRoomNumber.parse(text);
    }

    /*
    * 도메인 모델을 문자열로 변경하는 역할
    * */
    @Override
    public String print(HotelRoomNumber hotelRoomNumber, Locale locale) {
        return hotelRoomNumber.toString();
    }
}
