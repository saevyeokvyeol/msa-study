package com.msa.example.chapter09.controller;

import lombok.Getter;
import lombok.Setter;

/*
* 클래스 사용 예시
* GET /billing-codes -> ApiResponse<List<BillingCodeResponse>>
* POST /billing-codes -> ApiResponse<CreateCodeResponse>
* */
@Setter
@Getter
public class ApiResponse<T> {

    private boolean success;
    private String resultMessage;

    private T data;

    private ApiResponse(boolean success, String resultMessage) {
        this.success = success;
        this.resultMessage = resultMessage;
    }

    public ApiResponse() {
    }

    public static <T> ApiResponse ok(T data) {
        ApiResponse apiResponse = new ApiResponse(true, "success");
        apiResponse.data = data;
        return apiResponse;
    }
}
