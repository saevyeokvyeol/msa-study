package com.msa.example.chapter06.controller.resolver;

import com.msa.example.chapter06.controller.ClientInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ClientInfoArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String HEADER_CHANNEL = "X-SPRINGTOUR-CHANNEL";
    private static final String HEADER_CLIENT_IP = "X-FORWARDED-FOR";

    /*
    * 컨트롤러 클래스의 핸들러 메소드에 전달되는 파라미터가 ClientInfo.class 타입이면
    * ClientInfoArgumentResolver가 동작해 ClientInfo 파라미터에 데이터 바인딩
    * */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (ClientInfo.class.equals(parameter.getParameterType())) return true;
        return false;
    }

    /*
    * webRequest 객체의 getHeader() 메소드로 채널 이름과 클라이언트 IP 주소를 추출해 ClientInfo 객체 생성
    * */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String channel = webRequest.getHeader(HEADER_CHANNEL);
        String clientAddress = webRequest.getHeader(HEADER_CLIENT_IP);
        return new ClientInfo(channel, clientAddress);
    }
}
