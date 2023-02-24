package com.msa.example.chapter07;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class MiscTest {
    /*
    * 메소드 실행 순서
    * @BeforeAll setup()
    *
    * @BeforeEach init()
    * @Test testHashSetContainsNonDuplicatedValue()
    * @AfterEach cleanup()
    *
    * @BeforeEach init()
    * @Test testDummy()
    * @AfterEach cleanup()
    *
    * @AfterAll destroy()
    * */

    /*
    * @BeforeAll
    * : 테스트 클래스 실행 전 실행되는 메소드를 정의하기 위한 어노테이션
    *   단 한 번만 실행됨
    * */
    @BeforeAll
    public static void setup() {
        System.out.println("before all tests in the current test class");
    }

    /*
     * @BeforeEach
     * : 테스트 메소드 실행 전 실행되는 메소드를 정의하기 위한 어노테이션
     *   테스트 메소드가 실행될 때마다 실행됨
     * */
    @BeforeEach
    public void init() {
        System.out.println("before each @Test");
    }

    /*
     * @Test
     * : 테스트 메소드를 정의하기 위한 어노테이션
     * */
    @Test
    public void testHashSetContainsNonDuplicatedValue() {
        // Given: 입력 값 파트
        Integer value = Integer.valueOf(1);
        Set<Integer> set = new HashSet<>();

        // When: 실행 조건 파트
        set.add(value);
        set.add(value);
        set.add(value);

        // Then: 값 검증 파트
        Assertions.assertEquals(1, set.size()); // 예상 값과 실제 값이 같은지 비교
        Assertions.assertTrue(set.contains(value)); // 조건이 참인지 검증

        /*
        * 주요 검증 메소드
        *
        * assertNull(Object actual): 실제 값이 null인지 검증
        * assertNotNull(Object actual): 실제 값이 not null인지 검증
        *
        * assertTrue(boolean condition): 조건이 참인지 검증
        * assertTrue(boolean condition): 조건이 거짓인지 검증
        *
        * assertEquals(Object expect, Object actual): 예상 값과 실제 값이 같은지 비교(equals() 메소드 사용해 비교)
        * assertNotEquals(Object expect, Object actual): 예상 값과 실제 값이 다른지 비교(equals() 메소드 사용해 비교)
        *
        * assertSame(Object expect, Object actual): 예상 값과 실제 값이 같은지 비교(== 연산자 사용해 비교)
        * assertNotSame(Object expect, Object actual): 예상 값과 실제 값이 다른지 비교(== 연산자 사용해 비교)
        * */
    }

    @Test
    public void testDummy() {
        Assertions.assertTrue(Boolean.TRUE);
    }

    @Test
    public void cleanup() {
        System.out.println("after each @Test");
    }

    /*
     * @AfterAll
     * : 테스트 클래스 실행 후 실행되는 메소드를 정의하기 위한 어노테이션
     *   단 한 번만 실행됨
     * */
    @AfterAll
    public static void destroy() {
        System.out.println("after all tests in the current test class");
    }
}
