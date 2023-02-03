package com.msa.example.chapter03.annotation;

public interface Formatter<T> {
    /**
     * 제네릭 타입 T를 인자로 받아 포매팅하기 위한 추상 메소드
     * */
    String of(T target);
}
