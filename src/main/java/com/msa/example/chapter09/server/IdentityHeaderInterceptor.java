package com.msa.example.chapter09.server;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class IdentityHeaderInterceptor implements ClientHttpRequestInterceptor {

    // 인터셉터에서 사용하는 HTTP 헤더 이름과 값을 상수로 정의
    private static final String COMPONENT_HEADER_NAME = "X-COMPONENT-ID";
    private static final String COMPONENT_HEADER_VALUE = "HOTEL-API";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // RestTempleate을 호추하는 클래스에서 X-COMPONENT-ID 헤더를 설정하지 않았으면 기본값인 HOTEP-API 헤더값 입력
        request.getHeaders().addIfAbsent(COMPONENT_HEADER_NAME, COMPONENT_HEADER_VALUE);

        // ClientHttpRequestExecution의 execute() 메소드를 실행해 다음 인터셉터로 요청 전달
        return execution.execute(request, body);
    }
}
