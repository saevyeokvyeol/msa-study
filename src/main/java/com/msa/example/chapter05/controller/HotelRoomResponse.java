package com.msa.example.chapter05.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.msa.example.chapter05.controller.serializer.ToDollarStringSerializer;
import com.msa.example.chapter05.domain.HotelRoomType;
import com.msa.example.chapter05.util.IdGenerator;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class HotelRoomResponse {

    /*
    * @JsonProperty
    * : json 객체로 마셜링할 때 멤버 필드명과 다른 속성 이름을 사용하고 싶을 때 사용
    * */
    @JsonProperty("id")
    /*
    * @JsonSerialize
    * : json 객체로 마셜링할 때 멤버 필드 타입과 다른 타입으로 변환하고 싶을 때 사용
    *   반대로 json 객체를 자바 객체로 언마셜링할 때 멤버 필드 타입이 맞지 않으정 @JsonDeserialize 어노테이션 사용
    * */
    @JsonSerialize(using = ToStringSerializer.class)
    private final Long hotelRoomId;

    private final String roomNumber;

    private final HotelRoomType hotelRoomType;

    @JsonSerialize(using = ToDollarStringSerializer.class)
    private final BigDecimal originalPrice;

    private final List<Reservation> reservations;

    private HotelRoomResponse(Long hotelRoomId, String roomNumber, HotelRoomType hotelRoomType, BigDecimal originalPrice) {
        this.hotelRoomId = hotelRoomId;
        this.roomNumber = roomNumber;
        this.hotelRoomType = hotelRoomType;
        this.originalPrice = originalPrice;
        reservations = new ArrayList<>();
    }

    public static HotelRoomResponse of(Long hotelRoomId, String roomNumber, HotelRoomType hotelRoomType, BigDecimal originalPrice) {
        return new HotelRoomResponse(hotelRoomId, roomNumber, hotelRoomType, originalPrice);
    }

    public void reservedAt(LocalDate reservedAt) {
        reservations.add(new Reservation(IdGenerator.create(), reservedAt));
    }

    @Getter
    private static class Reservation {

        @JsonProperty("id")
        @JsonSerialize(using = ToStringSerializer.class)
        private final Long reservationId;

        /*
        * @JsonFormat
        * : java.util.Date나 java.util.Calendar 등의 객체를 사용자가 원하는 포맷으로 변경할 때 사용
        *   shape 속성은 해당 타입으로, pattern 속성은 해당 패턴으로 변경할 때 사용
        * */
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private final LocalDate reservedDate;

        public Reservation(Long reservationId, LocalDate reservedDate) {
            this.reservationId = reservationId;
            this.reservedDate = reservedDate;
        }
    }
}