package com.msa.example.chapter03.finalClass;

import java.io.Serializable;
import java.util.Currency;
import java.util.Objects;

/*
 * 정적 팩토리 메소드로 설계된 클래스는 반드시 final을 붙여 상속받을 수 없도록 함
 * */
public final class Money implements Serializable {

    private final Long value;
    private final Currency currency;

    /*
    * final class는 여러 상태가 있는 객체를 만들 수 없도록 기본 생성자 없이 모든 final 멤버 필드를 초기화할 수 있는 생성자 선언
    * 정적 팩토리 메소드로 설계될 경우 해당 생성자를 private로 만들어
    * */
    public Money(Long value, Currency currency) {
        if (value == null || value < 0) throw new IllegalArgumentException("invalid value = " + value);
        if (currency == null) throw new IllegalArgumentException("invalid currency");

        this.value = value;
        this.currency = currency;
    }

    /*
    * 불변 클래스는 멤버 필드 값을 바꿀 수 없도록 setter를 생략하되, 값에 접근할 수 있도록 getter는 제공
    * */
    public Long getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Money money = Money.class.cast(obj);
        return Objects.equals(value, money.value) && Objects.equals(currency, money.currency);
    }
}