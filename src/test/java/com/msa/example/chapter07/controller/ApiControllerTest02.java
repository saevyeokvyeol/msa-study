package com.msa.example.chapter07.controller;

import com.msa.example.chapter07.service.HotelDisplayService;
import com.msa.example.chapter07.util.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
* controllers 설정을 통해 테스트 대상 컨트롤러 클래스 설정 가능
* 해당 속성을 사용하지 않으면 어플리케이션 내의 모든 컨트롤러 클래스 스캔
* */
@WebMvcTest(controllers = HotelController.class)
public class ApiControllerTest02 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelDisplayService hotelDisplayService;

    @BeforeEach
    public void init() {
        // willAnswer() 메소드의 파라미터에서 Answer 인터페이스를 구현해 동적으로 값을 리턴
        given(hotelDisplayService.getHotelsByName(any()))
            .willAnswer(new Answer<List<HotelResponse>>() {
                @Override
                public List<HotelResponse> answer(InvocationOnMock invocationOnMock) throws Throwable {
                    HotelRequest hotelRequest = invocationOnMock.getArgument(0);
                    return List.of(new HotelResponse(1L, hotelRequest.getHotelName(), "unknown", "213-820-3xxx"));
                }
            });
    }

    @Test
    public void testGetHotelById() throws Exception {
        HotelRequest hotelRequest = new HotelRequest("Line Hotel");
        String jsonRequest = JsonUtil.objectMapper.writeValueAsString(hotelRequest);

        mockMvc.perform(post("/hotels/fetch-by-name")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].hotelId", Matchers.is(1)))
            .andExpect(jsonPath("$[0].hotelName", Matchers.is("Line Hotel")))
            .andDo(MockMvcResultHandlers.print(System.out));
    }
}
