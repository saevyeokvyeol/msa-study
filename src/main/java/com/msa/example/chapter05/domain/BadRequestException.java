package com.msa.example.chapter05.domain;

public class BadRequestException extends RuntimeException { // RuntimeException 상속 시 무조건 예외 처리 X
    private String errorMessage;

    public BadRequestException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
