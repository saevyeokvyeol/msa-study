package com.msa.example.chapter05.controller;

import com.msa.example.chapter05.domain.HotelRoomType;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class HotelRoomRequest {
    private String roomNumber;
    private HotelRoomType roomType; // 요청 메시지의 roomType과 매칭되기 때문에 멤버 필드명을 roomType으로 작성
    private BigDecimal originalPrice;

}
