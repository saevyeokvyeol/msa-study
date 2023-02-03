package com.msa.example.chapter03.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Formatter 클래스의 of() 메소드를 오버라이딩
     * LocalDateTime 파라미터를 받아 패턴에 맞게 포매팅해 리턴
     * */
    @Override
    public String of(LocalDateTime target) {
        return Optional.ofNullable(target).map(formatter::format).orElse(null);
    }
}
