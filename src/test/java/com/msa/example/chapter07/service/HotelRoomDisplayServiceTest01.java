package com.msa.example.chapter07.service;

import com.msa.example.chapter07.config.TestConfig;
import com.msa.example.chapter07.controller.HotelRoomResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
/*
* @ContextConfguration or @Import 어노테이션을 사용하여 명시적으로 테스트 자바 설정 클래스를 로딩
* 로딩된 클래스는 해당 테스트 클래스가 실행되는 동안 유효
* */
// @Import(value = {TestConfig.class})
@ContextConfiguration(classes= TestConfig.class)
/*
* 테스트 환경에 맞게 커스터마이징된 프로퍼티 파일을 로딩할 때 @TestPropertySource 어노테이션 사용
* locations 속성에는 프로퍼티 파일 경로 입력
* */
@TestPropertySource(locations = "classpath:application-test.properties")
public class HotelRoomDisplayServiceTest01 {
    @Autowired
    private HotelRoomDisplayService hotelRoomDisplayService;

    @Test
    public void testTestConfiguration() {
        HotelRoomResponse hotelRoomResponse = hotelRoomDisplayService.getHotelRoomById(1L);

        Assertions.assertNotNull(hotelRoomResponse);
        Assertions.assertEquals(1L, hotelRoomResponse.getHotelRoomId());
    }
}
