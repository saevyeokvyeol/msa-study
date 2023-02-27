package com.msa.example.chapter09.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


@Slf4j
@Component
public class WebClientConfig {
    // WebClient는 불변 객체이므로 여러 스레드가 동시에 접근해도 안전
    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
            .tcpConfiguration(tcpClient -> tcpClient
                // 커넥션이 생성될 때까지 최대 대기 시간을 밀리초 단위로 설정
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .doOnConnected(connection -> connection
                    //              읽기 최대 시간을 초 단위로 설정
                    .addHandlerLast(new ReadTimeoutHandler(10))
                    //              쓰기 최대 시간을 초 단위로 설정
                    .addHandlerLast(new WriteTimeoutHandler(10))
                )
            );

        // 생성한 HttpClient 객체를 사용해 ClientHttpConnector 객체 생성
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

        return WebClient.builder()
            // REST-API 서버 기본 URL 설정
            .baseUrl("http://localhost:3000")
            // 생성한 ClientHttpConnector 객체를 이용해 connector 객체 설정
            .clientConnector(connector)
            // REST-API를 호출하면 기본 설정으로 사용할 HTTP 헤더 설정
            .defaultHeaders(httpHeaders -> {
                httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            })
            .build();
    }
}
