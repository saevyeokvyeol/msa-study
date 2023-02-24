package com.msa.example.chapter07.controller;

import com.msa.example.chapter07.util.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

// 테스트 케이스 가독성을 높이기 위해 메소드 임포트
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
* @AutoConfigureMockMvc
* : MockMvc 객체를 스프링 빈으로 주입받기 위한 어노테이션
*   @SpringBootTest 어노테이션과 함께 스프링 MVC 기능 테스트 시 사용함
* */
@AutoConfigureMockMvc
// SpringBootTest.WebEnvironment.MOCK 설정으로 서블릿 컨테이너 실행하지 않고 목 서블릿 컨테이너 사용해 테스트
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApiControllerTest01 {
    // @AutoConfigureMockMvc로 생성된 MockMvc 주입
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetHotelById() throws Exception {
        HotelRequest hotelRequest = new HotelRequest("Ragged Point Inn");
        String jsonRequest = JsonUtil.objectMapper.writeValueAsString(hotelRequest);

        // 테스트 대상 REST-API는 URI가 "/hotels/fetch-by-name"인 POST 메소드
        mockMvc.perform(post("/hotels/fetch-by-name")
            // 요청 메시지 바디에 JSON 메시지인 jsonRequest 추가
            .content(jsonRequest)
            // 콘텐츠 타입을 json
            .contentType(MediaType.APPLICATION_JSON))
            // 응답 메시지 상태 코드가 '200 OK'인지 검증
            .andExpect(status().isOk())
            /*
            * MockMvcResultMatchers의 content() 메소드는 ContentResultMatchers 리턴
            * 리턴된 ContentResultMatchers를 이용해 콘텐츠 타입이 JSON인지 검증 가능
            * */
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].hotelId", Matchers.is(1000)))
            /*
            * MockMvcResultMatchers의 jsonPath() 메소드는 JSON 메시지 속성을 검증하는 JsonPathResultMatchers 리턴
            * 이 때 첫 번째 파라미터는 JSON path 표현식, 두 번째 파라미터는 기대값을 검증하는 Matchers 객체
            * */
            .andExpect(jsonPath("$[0].hotelName", Matchers.is("Ragged Point Inn")))
            .andDo(MockMvcResultHandlers.print(System.out));
    }
}
