package com.msa.example.chapter05.controller;

import com.msa.example.chapter05.controller.validator.HotelRoomReserveValidator;
import com.msa.example.chapter05.domain.reservation.ReserveService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
/*
* 별도로 관리해야 하는 검증 로직을 작성하기 위한 Controller 클래ㄷ셕
* */
@RestController
public class HotelRoomReserveController {
    private final ReserveService reserveService;

    public HotelRoomReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    /*
    * @InitBinder
    * : Spring Validator 사용 시 @Valid 어노테이션으로 검증이 필요한 객체를 가져오기 전 수행할 메소드를 지정하는 어노테이션
    * */
    @InitBinder
    /*
    * 초기화 함수의 리턴 타입은 void
    * 파라미터는 반드시 WebDataBinder를 받아야 함
    * */
    void initBinder(WebDataBinder binder) {
        // 파라미터로 넘어온 WebDataBinder 객체의 addValidators() 메소드를 이용해 Validator를 확장한 HotelRoomReserveValidator 클래스를 추가
        binder.addValidators(new HotelRoomReserveValidator());
    }

    @PostMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}/reserve")
    public ResponseEntity<HotelRoomIdResponse> reserveHotelRoomByRoomNumber(@PathVariable Long hotelId,
                                                                            @PathVariable String roomNumber,
                                                                            // 검증 대상인 HotelRoomReserveRequest 파라미터 앞에 @Valid 어노테이션 정의
                                                                            @Valid @RequestBody HotelRoomReserveRequest reserveRequest,
                                                                            // HotelRoomReserveValidator가 검증한 결과를 가져오는 파라미터
                                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String errorMessage = new StringBuilder(bindingResult.getFieldError().getCode())
                .append(" [").append(fieldError.getField()).append("] ")
                .append(fieldError.getDefaultMessage())
                .toString();

            System.out.println(reserveRequest.toString());
            return ResponseEntity.badRequest().build();
        }

        Long reservationId = reserveService.reserveHotelRoom(
            hotelId, roomNumber,
            reserveRequest.getCheckInDate(),
            reserveRequest.getCheckOutDate());

        HotelRoomIdResponse body = HotelRoomIdResponse.from(reservationId);
        return ResponseEntity.ok(body);
    }
}
