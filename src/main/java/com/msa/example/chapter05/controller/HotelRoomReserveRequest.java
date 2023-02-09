package com.msa.example.chapter05.controller;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class HotelRoomReserveRequest {
    /*
    * checkInDate는 논리적으로 checkOutDate 이후일 수 없으므로 서로 조합해 날짜를 검증해야 함
    * */
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String name;
}
