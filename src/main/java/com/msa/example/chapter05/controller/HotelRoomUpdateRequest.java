package com.msa.example.chapter05.controller;

import com.msa.example.chapter05.domain.HotelRoomType;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@ToString
public class HotelRoomUpdateRequest {
    /*
    * @NotNull
    * : 해당 멤버 필드가 null인지 검사
    *   null이라면 message 속성에 작성된 기본 에러 메시지를 출력
    *   empty string("")은 검사 X -> @NotEmpty로 검사해야 함
    * */
    @NotNull(message = "roomType can't be null")
    private HotelRoomType roomType;

    @NotNull(message = "originalPrice can't be null")
    /*
    * @Min
    * : 해당 멤버 필드의 최솟값을 검증
    *   value에 작성한 값보다 값이 크거나 같은지 검사
    * */
    @Min(value = 0, message = "originalPrice must be larger than 0")
    private BigDecimal originalPrice;

    /*
    * 그 외 hibernate validator 검증 어노테이션 종류
    *
    * @Pattern : 정규 표현식과 매칭되는지 검사
    * @Past : 과거 날짜인지 검사
    * @Size : 배열, 맵, 컬렉션 객체의 크기를 검증
    * @Max : 대상 값이 @Max 밸류보다 작거나 같은지 검사
    * @NotEmpty : 대상 값의 크기가 0보다 큰지 검사
    * @NotBlack : 대상 값을 trim한 후의 크기가 0보다 큰지 검사
    * @Email : 대상 값의 이메일 형식 여부를 정규 표현식으로 검사
    * @Length : 문자열의 길이가 min 값과 max 값 사이인지 검사
    * */
}
