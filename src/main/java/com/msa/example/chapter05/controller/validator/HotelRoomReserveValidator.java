package com.msa.example.chapter05.controller.validator;

import com.msa.example.chapter05.controller.HotelRoomReserveRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

public class HotelRoomReserveValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        /*
        * 검증 대상이 HotelRoomReserveRequest 객체이므로 clazz == HotelRoomReserveRequest.class여야 함
        * */
        return HotelRoomReserveRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        /*
        * HotelRoomReserveRequest.class의 cast() 메소드를 이용해 target을 HotelRoomReserveRequest 객체로 캐스팅함
        * 이 때 supports() 메소드로 확인한 객체만 validate()의 target 파라미터로 넘어오기 때문에 캐스팅 오류 X
        * */
        HotelRoomReserveRequest request = HotelRoomReserveRequest.class.cast(target);

        if (Objects.isNull(request.getCheckInDate())) {
            errors.rejectValue("checkInDate", "NotNull", "checkInDate is null");
            return;
        }

        if (Objects.isNull(request.getCheckOutDate())) {
            errors.rejectValue("checkOutDate", "NotNull", "checkOutDate is null");
            return;
        }

        /*
        * LocalDate 클래스의 compareTo() 메소드를 이용해 checkInDate가 checkOutDate보다 크면 Errors 객체에 검증 실패 메시지를 입력함
        * */
        if (request.getCheckInDate().compareTo(request.getCheckOutDate()) >= 0) {
            errors.rejectValue("checkOutDate", "Constraint Error", "checkOutDate is earlier than checkInDate");
            return;
        }
    }
}
