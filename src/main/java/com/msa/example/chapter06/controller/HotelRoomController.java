package com.msa.example.chapter06.controller;
import com.msa.example.chapter05.controller.HotelRoomResponse;
import com.msa.example.chapter05.domain.HotelRoomType;
import com.msa.example.chapter05.util.IdGenerator;
import com.msa.example.chapter06.domain.HotelRoomNumber;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@RestController
public class HotelRoomController {

/*    @GetMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public HotelRoomResponse getHotelRoomByPeriod(
        @PathVariable Long hotelId,
        // String 타입이었던 roomNumber를 HotelRoomNumber 타입으로 받음
        @PathVariable HotelRoomNumber roomNumber,
        @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate fromDate,
        @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate toDate) {

        Long hotelRoomId = IdGenerator.create();
        BigDecimal originalPrice = new BigDecimal("130.00");

                                                                    // roomNumber는 HotelRoomNumber 타입이므로 String으로 변환해야 파라미터로 전달 가ㅊ
        HotelRoomResponse response = HotelRoomResponse.of(hotelRoomId, roomNumber.toString(), HotelRoomType.DOUBLE, originalPrice);
        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate))
            fromDate.datesUntil(toDate.plusDays(1)).forEach(date -> response.reservedAt(date));

        return response;
    }*/

    @GetMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public HotelRoomResponse getHotelRoomByPeriod(
        // ClientInfoArgumentResolver가 resolveArgument() 메소드를 실행한 뒤 리턴된 ClientInfo를 해당 파라미터에 바인딩
        ClientInfo clientInfo,
        @PathVariable Long hotelId,
        @PathVariable String roomNumber,
        @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate fromDate,
        @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate toDate) {

        Long hotelRoomId = IdGenerator.create();
        BigDecimal originalPrice = new BigDecimal("130.00");

        HotelRoomResponse response = HotelRoomResponse.of(hotelRoomId, roomNumber, HotelRoomType.DOUBLE, originalPrice);
        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate))
            fromDate.datesUntil(toDate.plusDays(1)).forEach(date -> response.reservedAt(date));

        return response;
    }
}