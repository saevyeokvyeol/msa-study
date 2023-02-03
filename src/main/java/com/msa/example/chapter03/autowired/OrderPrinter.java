package com.msa.example.chapter03.autowired;

import com.msa.example.chapter03.annotation.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderPrinter {

    // 필드에 의존성 주입
    @Autowired
    @Qualifier("localDateTimeFormatter")
    private Formatter formatter01;

    private Formatter formatter02;
    // 파라미터에 의존성 주입
    @Autowired
    public void setFormatter02(@Qualifier("localDateTimeFormatter") Formatter formatter) {
        this.formatter02 = formatter;
    }

    private Formatter formatter03;
    // 생성자에 의존성 주입(@Autowired 생략 가능)
    @Autowired
    public OrderPrinter(@Qualifier("localDateTimeFormatter") Formatter formatter) {
        this.formatter03 = formatter;
    }
}