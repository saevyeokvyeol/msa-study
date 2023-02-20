package com.msa.example.chapter06.controller;

import com.msa.example.chapter05.controller.ErrorResponse;
import com.msa.example.chapter05.domain.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    // ErrorController에서 발생한 BadRequestException을 처리하기 위한 메소드
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        System.out.println("Error Message : " + ex.getErrorMessage());
        // errorConroller에서 만든 errorMessage을 포함해 REST-API 응답
        return new ResponseEntity<>(
            new ErrorResponse(ex.getErrorMessage()),
            HttpStatus.BAD_REQUEST
        );
    }
}
