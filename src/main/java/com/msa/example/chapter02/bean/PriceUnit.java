package com.msa.example.chapter02.bean;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Getter
public class PriceUnit {
    private final Locale locale;

    public PriceUnit(Locale locale) {
        if (Objects.isNull(locale)) throw new IllegalArgumentException("locale arg is null");

        this.locale = locale;
    }

    /**
     * BigDecimal 타입 파라미터와 PriceUnit 클래스 속성인 locale을 사용해 적합한 화폐 포맷으로 변경
     * */
    public String format(BigDecimal price) {
        /*
        * NumberFormat 클래스는 숫자 문자열을 파싱하거나 숫자를 특정 형태로 포매팅
        * 스레드 안전하지 않아 멀티 스레드 환경에서 제대로 동작하지 않기 떄문에 메소드 내부에서 매번 객체를 생성해줘야 함
        * - 스레드 안전: 멀티 스레드 프로그래밍에서 어떤 공유 자원(함수, 변수, 객체)에 여러 스레드가 동시에 접근해도 프로그램 실행에 문제가 없는 상태
        * */
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        return currencyFormat.format(Optional.ofNullable(price).orElse(BigDecimal.ZERO));
    }

    /**
     * locale이 null인지 검사한 뒤 null일 경우 예외를 생성해 던짐
     * 클래스 변수 locale은 null이 될 수 없는 클래스 불변식을 갖고 있음
     * - 클래스 불변식: 어떤 객체가 정상적으로 동작하기 위해 항상 참이어야 하는 조건
     * */
    public void validate() {
        if (Objects.isNull(locale)) throw new IllegalStateException("locale is null");
        log.info("locale is [{}]", locale);
    }
}
