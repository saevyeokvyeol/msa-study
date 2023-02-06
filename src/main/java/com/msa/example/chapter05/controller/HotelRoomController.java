package com.msa.example.chapter05.controller;

import com.msa.example.chapter05.domain.HotelRoomType;
import com.msa.example.chapter05.util.IdGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/*
* @RestController = @Controller + @ResponseBody
* 해당 어노테이션이 사용된 클래스 메소드가 리턴하는 객체는 JSON으로 자동 마셜링됨
* */
@RestController
public class HotelRoomController {

    /*
    * @GetMapping
    * : =@RequestMapping(method = RequestMethod.GET)
    *   GET HTTP 메소드를 사용하는 사용자 요청을 매핑하는 어노테이션
    *   @RequestMapping이나 @GetMapping같은 어노테이션이 사용되어 사용자 요청을 처리하는 메소드를 핸들러 메소드라 부름
    * */
    @GetMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public HotelRoomResponse getHotelRoomByPeriod(
        /*
        * @PathVariable
        * : 사용자가 요청한 URI에서 {파라미터명} 형태의 리소스 변수를 찾아 파라미터로 전달해주는 어노테이션
        *   @RequestParam과는 달리 path에 알맞은 형태의 리소스 변수가 선언되어야 전달 가능
        * */
        @PathVariable Long hotelId,
        @PathVariable String roomNumber,
        /*
        * @RequestParam
        * : 사용자가 요청한 메시지 파라미터에서 파라미터명과 일치하는 키값의 밸류를 찾아 파라미터로 전달해주는 어노테이션
        * @DateTimeFormat
        * : 파라미터 값을 패턴에 맞춰 파싱해 파라미터 객체로 변환하는 어노테이션
        * */
        @RequestParam(value="fromDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate fromDate,
        @RequestParam(value="toDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate toDate) {

        Long hotelRoomId = IdGenerator.create();
        BigDecimal originalPrice = new BigDecimal("130.00");

        HotelRoomResponse response = HotelRoomResponse.of(hotelRoomId, roomNumber, HotelRoomType.DOUBLE, originalPrice);
        if (Objects.nonNull(fromDate) && Objects.nonNull(toDate)) {
            fromDate.datesUntil(toDate.plusDays(1)).forEach(date -> response.reservedAt(date));
        }
        return response;
    }

    @DeleteMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public DeleteResultResponse deleteHotelRoom(
        @PathVariable Long hotelId,
        @PathVariable String roomNumber) {
        System.out.println("Delete Request. hotelId=" + hotelId + ", roomNumber=" + roomNumber);
        return new DeleteResultResponse(Boolean.TRUE, "success");
    }
}