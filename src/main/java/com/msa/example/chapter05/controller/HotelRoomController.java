package com.msa.example.chapter05.controller;

import com.msa.example.chapter05.domain.HotelRoomType;
import com.msa.example.chapter05.util.IdGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/*
* @RestController = @Controller + @ResponseBody
* 해당 어노테이션이 사용된 클래스 메소드가 리턴하는 객체는 JSON으로 자동 마셜링됨
* */
@RestController
public class HotelRoomController {

    private static final String HEADER_CREATED_AT = "X-CREATED-AT";
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

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

    /*
     * @DeleteMapping
     * : =@RequestMapping(method = RequestMethod.DELETE)
     *   DELETE HTTP 메소드를 사용하는 사용자 요청을 매핑하는 어노테이션
     * */
    @DeleteMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public DeleteResultResponse deleteHotelRoom(
        @PathVariable Long hotelId,
        @PathVariable String roomNumber) {
        System.out.println("Delete Request. hotelId=" + hotelId + ", roomNumber=" + roomNumber);
        return new DeleteResultResponse(Boolean.TRUE, "success");
    }

    /*
     * @PostMapping
     * : =@RequestMapping(method = RequestMethod.POST)
     *   POST HTTP 메소드를 사용하는 사용자 요청을 매핑하는 어노테이션
     * */
    @PostMapping(path = "/hotels/{hotelId}/rooms")
    public ResponseEntity<HotelRoomIdResponse> createHotelRoom(
        @PathVariable Long hotelId,
        /*
        * @RequestBody
        * : 클라이언트에서 전송한 요청 메시지 바디를 언마셜링해 자바 객체로 변환한 뒤 파라미터로 전달해주는 어노테이션
        * */
        @RequestBody HotelRoomRequest hotelRoomRequest) {
        System.out.println(hotelRoomRequest.toString());

        /*
        * MultiValueMap 객체를 이용해 HTTP 헤더 설정
        * */
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HEADER_CREATED_AT, DATE_FORMATTER.format(ZonedDateTime.now()));
        HotelRoomIdResponse body = HotelRoomIdResponse.from(1_002_003_004L);

        /*
        * 클라이언트에서 전달한 body와 headers, 상태 코드 200 OK를 사용자에게 전달
        * */
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    @PutMapping(path = "/hotels/{hotelId}/rooms/{roomNumber}")
    public ResponseEntity<HotelRoomIdResponse> updateHotelRoomByRoomNumber(@PathVariable Long hotelId,
                                                                           @PathVariable String roomNumber,
                                                                           /*
                                                                           * HotelRoomUpdateRequest 멤버 필드 중 검증 어노테이션이 사용된 필드 검사
                                                                           * */
                                                                           @Valid @RequestBody HotelRoomUpdateRequest hotelRoomUpdateRequest,
                                                                           /*
                                                                           * BindingResult 파라미터는 @Valid 어노테이션 검증 결과와 결과를 조회할 수 있는 메소드를 제공함
                                                                           * */
                                                                           BindingResult bindingResult) {
        // 검증 결과에 에러가 하나라도 존재한다면
        if (bindingResult.hasErrors()) {
            // 검증 대상 클래스 속성의 검증 실패 정보를 변수에 저장
            FieldError fieldError = bindingResult.getFieldError();
            String errorMessage = new StringBuilder("validation error.")
                .append(" field : ").append(fieldError.getField()) // 검증 실패한 속성 이름
                .append(", code : ").append(fieldError.getCode()) // 검증 실패 코드
                .append(", message : ").append(fieldError.getDefaultMessage()) // 검증 어노테이션의 message 속성 값 리턴
                .toString();

            System.out.println(errorMessage);
            /*
            * ResponseEntity.badRequest() 메소드는 400 Bad Request 상태 코드를 설정한 뒤 BodyBuilder 객체를 리턴함
            * BodyBuilder.build() 메소드는 ResponseEntity 객체를 만들어 응답
            * */
            return ResponseEntity.badRequest().build();
        }

        System.out.println(hotelRoomUpdateRequest.toString());
        HotelRoomIdResponse body = HotelRoomIdResponse.from(1_002_003_004L);
        return ResponseEntity.ok(body);
    }
}