package com.msa.example.chapter07.service;

import com.msa.example.chapter07.controller.HotelRequest;
import com.msa.example.chapter07.controller.HotelResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;

/*
* @SpringBootTest: 스프링 프레임워크 기능 + 대상 클레스 테스트용 어노테이션
*                  Junit4까지는 @RunWith(SpringRunner.class) 어노테이션과 함께 사용해야 함
*                  webEnvironment 설정으로 웹 테스트 환경 설정 가능
* webEnvironment 설정 종류
* : WebEnvironment.MOCK - 서블릿 컨테이너를 실행하지 않고 서블릿을 목으로 만들어 테스트 진행
*   WebEnvironment.RANDOM_PORT - 랜덤한 포트값으로 서블릿 컨테이너를 실행해 테스트 진행
*   WebEnvironment.DEFINED_PORT - application.properties에 정의된 포트를 사용해 서블릿 컨테이너를 실행해 테스트 진행
*   WebEnvironment.NONE - 서블릿 환경을 구성하지 않고 테스트 실행
* */
@SpringBootTest()
public class HotelDisplayServiceTest {
    private final HotelDisplayService hotelDisplayService;
    private final ApplicationContext applicationContext;

    @Autowired
    public HotelDisplayServiceTest(HotelDisplayService hotelDisplayService, ApplicationContext applicationContext) {
        this.hotelDisplayService = hotelDisplayService;
        this.applicationContext = applicationContext;
    }

    @Test
    public void testReturnOneHotelWhenRequestIsHotelName() {
        // Given
        HotelRequest hotelRequest = new HotelRequest("Line Hotel");

        // When: 주입받은 hotelDisplayService 스프링 빈 객체 메소드 테스트
        List<HotelResponse> hotelResponses = hotelDisplayService.getHotelsByName(hotelRequest);

        // Then
        Assertions.assertNotNull(hotelResponses);
        Assertions.assertEquals(1, hotelResponses.size());
    }

    @Test
    public void testApplicationContext() {
        /*
        * 주입받은 applicationContext 객체의 getBean() 메소드를 이용해 DisplayService 타입 스프링 빈을 가져옴
        * getBean()이 스프링 빈 객체를 정상적으로 가져오지 못 할 시 테스트 시래
        * */
        DisplayService displayService = applicationContext.getBean(DisplayService.class);

        Assertions.assertNotNull(displayService);
        Assertions.assertTrue(HotelDisplayService.class.isInstance(displayService));
    }
}
