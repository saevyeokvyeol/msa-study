package com.msa.example.chapter02.scope;

import com.msa.example.chapter02.annotation.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter implements Formatter<Date> {

    /*
    * SimpleDateFormat 클래스는 멀티 스레드에 안전하지 않아 클래스 속성(=멤버 필드)로 사용하면 안 됨
    * -> 스레드 환경에서 오류 가능성 있음
    * */
    private SimpleDateFormat dateFormat;

    /*
     * 스프링 빈 컨테이너가 주입한 파라미터를 이용해 생성 시 멤버 필드 초기화
     * 파라미터로 null이 들어올 경우 에러 발생
     * */
    public DateFormatter(String pattern) {
        if (StringUtils.isEmpty(pattern)) throw new IllegalArgumentException("Pattern is empty");

        this.dateFormat = new SimpleDateFormat(pattern);
    }

    /*
     * 생성자에서 초기화한 멤버 필드를 이용해 포매팅
     * */
    @Override
    public String of(Date target) {
        return dateFormat.format(target);
    }

    /*
     * 생성자에서 초기화한 멤버 필드를 이용해 포매팅
     * */
    public Date parse(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }
}
