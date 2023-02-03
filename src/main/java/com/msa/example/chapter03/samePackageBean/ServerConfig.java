package com.msa.example.chapter03.samePackageBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public String datePattern() {
        return "yyyy-MM-dd'T'HH:mm:ss.XXX";
    }

    @Bean
    public DateFormatter defaultDateFormatter() {
        /*
        * datePattern() 리턴 값인 "yyyy-MM-dd'T'HH:mm:ss.XXX"이 파라미터로 들어가는 생성자로 객체 생성
        * 스프링 빈 객체인 datePattern()을 사용하면 스프링 빈 컨테이너가 자동으로 의존성을 주입해줌
        * */
        return new DateFormatter(datePattern());
    }
}
