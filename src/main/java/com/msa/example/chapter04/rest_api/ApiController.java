package com.msa.example.chapter04.rest_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class ApiController {
    private HotelSearchService hotelSearchService;

    public ApiController(HotelSearchService hotelSearchService) {
        this.hotelSearchService = hotelSearchService;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/hotels/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId) {
        Hotel hotel = hotelSearchService.getHotelById(hotelId);

        /*
        * ResponseEntity
        * : HTTP 헤더와 상태 코드, 바디 메세지를 포함하는 HTTP 응답 메시지를 추상화한 것
        *   ok() 메소드는 200 OK 상태 코드 값을 설정해 ResponseEntity 객체 생성 후 반환
        *   accepted(), notFound(), internalServerError(), badRequest() 메소드를 사용해 다른 상태 코드 반환도 가능
        * */
        return ResponseEntity.ok(hotel);
    }
}
