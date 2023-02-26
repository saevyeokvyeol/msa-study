package com.msa.example.chapter09.config;

import com.msa.example.chapter09.server.IdentityHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        // ClientHttpRequestFactory 구현체로 SimpleClientHttpRequestFactory 사용
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        // 클라이언트와 서버 사이에서 커넥션 객체 생성 최대 시간 설정(밀리초)
        factory.setConnectTimeout(3000);
        // 클라이언트가 서버에 데이터 처리를 요청하고 응답받기까지의 최대 시간 설정
        factory.setReadTimeout(1000);
        // SimpleClientHttpRequestFactory 요청 바디 버퍼링 여부 설정(디폴트값: true)
        factory.setBufferRequestBody(false);

        return factory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        // ClientHttpRequestFactory를 파라미터로 받아 RestTemplate 생성자 파라미터로 설정
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        /*
        * RestTemplate에 새 인터셉스 객체 추가
        * 1. getInterceptors() 메소드에서 인터셉터 리스트 객체 리턴
        * 2. add() 메소드로 IdentityHeaderInterceptor 객체 추가
        * */
        restTemplate.getInterceptors().add(new IdentityHeaderInterceptor());
        /*
        * RestTemplate 에러 핸들링 설정
        * RestTemplate 에러 핸들러 기본값은 DefaultResponseErrorHandler()
        * */
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        return restTemplate;
    }
}
