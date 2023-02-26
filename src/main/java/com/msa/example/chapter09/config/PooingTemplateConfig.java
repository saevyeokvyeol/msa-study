package com.msa.example.chapter09.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PooingTemplateConfig {
    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

        // 커넥션 풀에서 관리할 수 있는 최대 커넥션 갯수 설정
        manager.setMaxTotal(100);
        //커넥션 풀의 루트마다 관리할 수 있는 최대 커넥션 갯수
        manager.setDefaultMaxPerRoute(5);

        HttpHost httpHost = new HttpHost("10.192.10.111", 8888, "http");
        /*
        * 커넥션 풀의 특정 루트마다 관리할 수 있는 최대 커넥션 개수 설정(setDefaultMaxPerRoute() 설정 덮어씀)
        * 여기에서는 HttpRoute() 생성자에 위에서 선언한 HttpHost을 넣어 루트를 특정
        * */
        manager.setMaxPerRoute(new HttpRoute(httpHost), 10);

        return manager;
    }

    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom()
            // PoolingHttpClientConnectionManager에서 커넥션을 요청해서 응답받기까지 최대 3000ms까지만 대기하도록 설정
            .setConnectionRequestTimeout(3000)
            // 서버와 클라이언트 사이에서 커넥션을 생성할 수 있는 Timeout 3000ms로 설정
            .setConnectTimeout(3000)
            // HTTP 요청 메시지를 전달한 후 HTTP 응답 메시지를 받기까지 Timeout 1000ms로 설정
            .setSocketTimeout(1000)
            .build();
    }

    @Bean
    public CloseableHttpClient httpClient() {
        // HttpClientBuilder을 이용해 CloseableHttpClient 생성
        return HttpClientBuilder.create()
            // PoolingHttpClientConnectionManager 스프링 빈을 주입받아 설정
            .setConnectionManager(poolingHttpClientConnectionManager())
            // RequestConfig 스프링 빈 주입받아 설정
            .setDefaultRequestConfig(requestConfig())
            .build();
    }

    @Bean
    public RestTemplate poolingRestTemplate() {
        // HttpComponentsClientHttpRequestFactory 객체 생성
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        /*
        * HttpComponentsClientHttpRequestFactory에 CloseableHttpClient 스프링 빈 주입받아 설정
        * 이 때 CloseableHttpClient는 커넥션 풀을 사용할 수 있기 때문에 아래에서 생성하는 RestTemplate에서도 커넥션 풀 사용 가능
        * */
        requestFactory.setHttpClient(httpClient());
        // HttpComponentsClientHttpRequestFactory을 이용해 RestTemplate
        return new RestTemplate(requestFactory);
    }
}
