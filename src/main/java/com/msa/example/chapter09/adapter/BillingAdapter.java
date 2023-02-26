package com.msa.example.chapter09.adapter;

import com.msa.example.chapter09.controller.ApiResponse;
import com.msa.example.chapter09.controller.BillingCodeResponse;
import com.msa.example.chapter09.controller.CreateCodeRequest;
import com.msa.example.chapter09.controller.CreateCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class BillingAdapter {

    // ParameterizedTypeReference를 사용해 중첩된 클래스 타입인 ApiResponse<CreateCodeResponse> 정의
    private static final ParameterizedTypeReference<ApiResponse<CreateCodeResponse>> TYPE_REFERENCE;

    static {
        TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };
    }

    public CreateCodeResponse createBillingCode(List<Long> hotelIds) {
        /*
         * UriComponentsBuilder
         * : 빌더 패턴을 사용해 URI 객체를 생성
         *   예제는 그 중에서도 fromPath() 메소드 사용
         *   파라미터로 서버 리소스 경로, scheme() 메소드로 http 프로토콜 설정, host() 메소드로 서버 주소 설정, port() 메소드로 서버 포트 설정
         * */
        URI uri = UriComponentsBuilder.fromPath("/billing-codes")
            .scheme("http").host("127.0.0.1").port(8888)
            .build(false).encode()
            .toUri();

        CreateCodeRequest request = new CreateCodeRequest(1, hotelIds);

        HttpHeaders headers = new HttpHeaders();

        // 요청 메시지 바디가 JSON 메시지이기 때문에 headers에 Content-type 헤더 추가
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 위에서 만든 headers 객체를 파라미터로 사용해 HTTP 요청 메시지를 만들기 위한 HttpEntity 객체 생성
        HttpEntity<CreateCodeRequest> httpEntity = new HttpEntity<>(request, headers);

        /*
        * exchange() 메소드는 HTTP 통신에 사용할 HTTP 메소드를 HttpStatus 열거형 상수로 설정 가능
        *
        * 파라미터
        * 1. 서버에 요청할 REST-API 경로
        * 2. 기본값인 HttpMethod.GET 대신 HttpMethod.POST 사용
        * 3. HTTP 요청 메시지로 httpEntity 사용
        * 4. 응답 메시지를 TYPE_REFERENCE(=ParameterizedTypeReference<ApiResponse<CreateCodeResponse>>) 타입으로 변환해 리턴
        * */
        ResponseEntity<ApiResponse<CreateCodeResponse>> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, TYPE_REFERENCE);

        /*
         * responseEntity의 getStatusCode()는 HTTP 응답 메시지의 상태 코드 값을 HttpStatus 열거형으로 변환해 응답
         * -> 이를 이용해 성공 여부 판단 가능
         * 예제에서는 HttpStatus.OK일 경우 성공으로 판단하기 때문에 HttpStatus.OK가 아닐 경우 에러 던짐
         * */
        if (HttpStatus.OK != responseEntity.getStatusCode()) {
            log.error("Error from Billing. status:{}, hotelIds:{}", responseEntity.getStatusCode(), hotelIds);
            throw new RuntimeException("Error from Billing. " + responseEntity.getStatusCode());
        }

        // 타입 캐스팅 없이 클래스 타입에 안전하도록 CreateCodeResponse 객체 리턴
        return responseEntity.getBody().getData();
    }

    private final RestTemplate restTemplate;

    public BillingAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BillingCodeResponse> getBillingCodes(String codeNameParam) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/billing-codes")
            .scheme("http").host("127.0.0.1").port(8888);

        if (Objects.nonNull(codeNameParam)) builder.queryParam("codeName", codeNameParam);

        URI uri = builder.build(false).encode().toUri();

        /*
        * RestTemplate의 getForEntity() 메소드는 HTTP GET을 기본으로 사용
        * 서버에 HTTP 요청을 할 때 파라미터로 받은 객체를 사용하며, 여러 형태의 파라미터를 받을 수 있도록 오버로딩
        * 예제는 URI와 클래스 타입을 넘겨주며, URI에 정의된 REST-API 경로를 사용해 서버에 요청한 뒤 응답 메시지를 클래스 타입으로 변환해 리턴함
        * */
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity(uri, ApiResponse.class);

        if (HttpStatus.OK != responseEntity.getStatusCode()) {
            log.error("Error from Billing. status:{}, param:{}", responseEntity.getStatusCode(), codeNameParam);
            throw new RuntimeException("Error from Billing. " + responseEntity.getStatusCode());
        }

        // getForEntity() 메소드 파라미터로 사용된 클래스 타입으로 HTTP 응답 메시지의 바디 값 리턴
        ApiResponse apiResponse = responseEntity.getBody();
        return (List<BillingCodeResponse>) apiResponse.getData();
    }

    public CreateCodeResponse create(List<Long> hotelIds) {
        URI uri = UriComponentsBuilder.fromPath("/billing-codes")
            .scheme("http").host("127.0.0.1").port(8888)
            .build(false).encode()
            .toUri();

        // POST /billing-codes API 요청 메시지 바디를 추상화한 CreateCodeRequest 객체 생성
        CreateCodeRequest request = new CreateCodeRequest(1, hotelIds);

        // 생성한 CreateCodeRequest 객체를 postForEntity() 메소드 body 파라미터로 입력
        ResponseEntity<ApiResponse> responseEntity = restTemplate.postForEntity(uri, request, ApiResponse.class);

        if (HttpStatus.OK != responseEntity.getStatusCode()) {
            log.error("Error from Billing. status:{}, hotelIds:{}", responseEntity.getStatusCode(), hotelIds);
            throw new RuntimeException("Error from Billing. " + responseEntity.getStatusCode());
        }

        ApiResponse apiResponse = responseEntity.getBody();
        /*
        * POST /billing-codes API의 JSON 메시지 중 data 속성 값을 map으로 캐스팅
        * JSON에서 data는 "codes":[111, 222, 333] 형태이므로 map은 <String, List<Long>> 형태
        * */
        Map<String, List<Long>> dataMap = (Map) apiResponse.getData();
        // 위에서 캐스팅한 dataMapdptj 필요한 밸류를 키값인 codes로 빼온 후 CreateCodeResponse의 of() 파라미터로 전달
        return CreateCodeResponse.of(dataMap.get("codes"));
    }
}
