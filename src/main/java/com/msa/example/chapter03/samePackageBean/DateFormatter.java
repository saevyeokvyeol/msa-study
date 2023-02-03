package com.msa.example.chapter03.samePackageBean;

import com.msa.example.chapter03.annotation.Formatter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateFormatter implements Formatter<Date> {

    private SimpleDateFormat simpleDateFormat;

    /*
    * 스프링 빈 컨테이너가 주입한 파라미터를 이용해 생성 시 멤버 필드 초기화
    * */
    public DateFormatter(String pattern) {
        this.simpleDateFormat = new SimpleDateFormat(pattern);
    }

    /*
    * 생성자에서 초기화한 멤버 필드를 이용해 포매팅
    * */
    @Override
    public String of(Date target) {
        return simpleDateFormat.format(target);
    }
}
