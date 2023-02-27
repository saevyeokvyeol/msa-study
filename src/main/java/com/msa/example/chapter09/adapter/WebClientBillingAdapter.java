package com.msa.example.chapter09.adapter;

import com.msa.example.chapter09.controller.ApiResponse;
import com.msa.example.chapter09.controller.CreateCodeRequest;
import com.msa.example.chapter09.controller.CreateCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
public class WebClientBillingAdapter {
    private static final ParameterizedTypeReference<ApiResponse<CreateCodeResponse>> TYPE_REFERENCE;

    static {
        TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };
    }
    private final WebClient webClient;

    public WebClientBillingAdapter(WebClient billingWebClient) {
        this.webClient = billingWebClient;
    }

    public CreateCodeResponse createBillingCode(List<Long> hotelIds) {
        URI uri = UriComponentsBuilder.fromPath("/billing-codes")
            .scheme("http").host("127.0.0.1").port(8888)
            .build(false).encode()
            .toUri();
        CreateCodeRequest request = new CreateCodeRequest(1, hotelIds);
        //              mutate() 메소드를 이용해 WebClient.Builder 재사용(설정 후 build() 메소드 사용 시 다시 객체가 리턴됨)
        return webClient.mutate().build()
            // HTTP 메소드 설정
            .method(HttpMethod.POST)
            // uri 설정: 기존에 WebClientConfig에서 설정한 baseUrl() 설정이 있다면 덮어씀
            .uri(uri)
            // HTTP 요청 메시지 바디 설정
            .bodyValue(request)
            // 서버의 REST-API 실행
            .retrieve()
            // HTTP 상태 코드 숫자를 사용해 에러 처리용 함수를 제공
            .onStatus(httpStatus -> HttpStatus.OK != httpStatus,
                      response -> Mono.error(new RuntimeException("Error from Billing." + response.statusCode().value())))
            // ParameterizedT ypeReference 상수 TYPE_REFERENCE를 사용해 바디를 Mono로 응답
            .bodyToMono(TYPE_REFERENCE)
            // 리턴받은 Mono를 Flux로 변환해 다시 Java 8 Stream 객체로 변환
            .flux().toStream()
            // 변환된 Stream 객체에서 첫 번째 객체를 리턴하는 findFirst() 메소드를 이용해 Optional<ApiResponse>로 변환
            .findFirst()
            .map(ApiResponse::getData)
            .orElseThrow(() -> new RuntimeException("Empty response"));
    }
}
