package com.msa.example.chapter07.service;

import com.msa.example.chapter07.controller.HotelRoomResponse;
import com.msa.example.chapter07.domain.HotelRoomEntity;
import com.msa.example.chapter07.repository.HotelRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class HotelRoomDisplayServiceTest02 {
    @Autowired
    private HotelRoomDisplayService hotelRoomDisplayService;

    /*
    * HotelRoomRepository에 @MockBean 어노테이션을 사용해 목 객체 생성
    * 어노테이션 선언 시 테스트 프레임워크가 만든 HotelRoomRepository 목 객체가 ApplicationContext에 포함되며,
    * 해당 목 객체는 HotelRoomDisplayService의 ApplicationContext로 주입
    * */
    @MockBean
    private HotelRoomRepository hotelRoomRepository;

    @Test
    public void testMockBean() {
        /*
        * 목 객체의 행동 설정
        * : hotelRoomRepository의 findById() 메소드가 호출되면 호텔 객실 아이디가 10L인 HotelRoomEntity 객체 리턴
        *   findById() 메소드에 ArgumentMatchers.any()(== 어떤 파라미터)를 설정
        *   -> 인수 값에 상관없이 willReturn() 메소드의 인사로 설정된 HotelRoomEntity 객체 무조건 리턴
        * */
        BDDMockito.given(this.hotelRoomRepository.findById(ArgumentMatchers.any()))
            .willReturn(new HotelRoomEntity(10L, "test", 1, 1, 1));

        HotelRoomResponse hotelRoomResponse = hotelRoomDisplayService.getHotelRoomById(1L);

        Assertions.assertNotNull(hotelRoomResponse);
        /*
        * 테스트 케이스에서 설정된 HotelRoomRepository 목 객체는 항상 hotelRoomId가 10L인 hotelRoomEntity 리턴
        * 때문에 hotelRoomId가 10L인지 검증
        * */
        Assertions.assertEquals(10L, hotelRoomResponse.getHotelRoomId());

        /*
        * 목 객체 행동 설정 메소드
        *
        * BDDMockito.given(T methodCall)
        * : 스텁(개발자가 원하는 결과를 응답하는 메소드)을 만드는 메소드
        *   파라미터에 스텁으로 만들 대상 메소드 입력
        *
        * ArgumentMatchers.any()
        * : 모든 타입의 값을 의미하는 메소드
        *   ArgumentMatchers 클래스는 파라미터 값에 대한 조건을 설정할 때 사용함
        *
        * BDDMockito.willReturn(T value)
        * : 생성한 조건에 맞는 스텁이 호출되면 파라미터로 입력된 값을 응답하는 메소드
        * */

        /*
        * ArgumentMatchers 주요 메소드
        *
        * public static <T> T any(Class<T> type)
        * : type 객체인 경우(null 제외)
        *
        * public static <T> T isA(Class<T> type)
        * : type 객체를 구현한 객체인 경우
        *
        * public static boolean anyBoolean()
        * : boolean 타입인 경우
        *
        * public static byte anyByte()
        * : byte, Byte 타입인 경우
        *
        * public static byte anyInt()
        * : int, Integer 타입인 경우
        *   비슷한 메소드로 anyLong(), anyFloat(), anyDouble(), anyShort(), anyString()이 있음
        *
        * public static <T> List<T> anyList()
        * : List 타입인 경우
        *   비슷한 메소드로 anySet(), anyMap()이 있음
        *
        * public static int eq(int value)
        * : value 값과 일치하는 경우
        *   다양한 파라미터로 오버로딩되어 int 이외의 값도 입력 가능
        * */

        /*
        * willReturn() 오버로딩 메소드 종류
        *
        * public static BDDSubber willReturn(Object toBeReturned)
        * : 조건이 맞을 경우 정해진 값 리턴
        *
        * public static BDDSubber willReturn(Object toBeReturned, Object... toBeReturnedNext)
        * : 조건이 맞을 경우 정해진 값을 리턴
        *   계속 스텁 메소드를 호출할 경우 toBeReturnedNext 파라미터가 순서대로 리턴됨
        *
        * public static BDDSubber willReturn()
        * : 아무 것도 리턴하지 않음
        *
        * public static BDDSubber willThrow(Class<? extends Throwable> toBeThrown)
        * : 조건이 맞을 경우 toBeThrown 예외 응답
        *
        * public static BDDSubber will(Answer<?> answer)
        * : Answer 인터페이스를 사용해 개발자가 원하는 응답 프로그래밍 가능
        *   고정된 값만 리턴 가능한 willReturn()과는 달리 다양한 값을 동적으로 리턴 가능
        *
        * public static BDDSubber willAnswer(Answer<?> answer)
        * : will()과 동일
        * */
    }
}
