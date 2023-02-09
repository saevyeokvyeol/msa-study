package com.msa.example.chapter05.controller;

import com.msa.example.chapter05.domain.BadRequestException;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* @RestControllerAdvice
* : @ExceptionHandler 어노테이션이 정의된 메소드를 포함하는 스프링 빈 어노테이션
* */
@RestControllerAdvice
public class ApiExceptionHandler {
    /*
    * @ExceptionHandler
    * : 속성으로 입력한 예외가 발생하면 처리할 메소드를 지정하는 어노테이션
    * */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handlerBadRequestException(BadRequestException ex) { // 파라미터에 에러를 작성하면 스프링 프레임워크에서 발생할 시 자동으로 주입함
        System.out.println("Error Message : " + ex.getErrorMessage());
        return new ResponseEntity<>(new ErrorResponse(ex.getErrorMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("system error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
