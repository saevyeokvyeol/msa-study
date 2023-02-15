package com.msa.example.chapter06.server;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class LoggingFilter implements Filter {
    /*
    * 사용자 요청 직후 선처리되고 사용자 응답 직전 후처리되는 작업을 진행하는 메소드
    * */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("선처리 작업");

        /*
        * 사용자가 요청한 작업을 실행
        * 해당 코드 이전에 작성된 코드는 선처리되고, 해당 코드 이후에 작성된 코드는 후처리됨
        * */
        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("후처리 작업");
    }
}
