package com.msa.example.chapter06.controller;

import com.msa.example.chapter05.domain.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Locale;

@Slf4j
@RestController
public class ErrorController {
    private MessageSource messageSource;

    /*
    * 파라미터로 들어오는 MessageSource 객체는 스프링 부트 자동 설정을 통해 제작되며,
    * 메시지 프로퍼티 파일에서 적절한 메시지 프로퍼티 값을 참조해오는 역할
    * */
    public ErrorController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping(path = "/error")
    public void createError() {
        // getLocale() 메소드는 LocaleContextHolder 클래스의 스태틱 메소드로 ThreadLocal에 저장된 Locale 객체를 응답함
        Locale locale = LocaleContextHolder.getLocale();

        String[] args = {"10"};
        /*
        * 1. 메시지 프로퍼티 파일에서 main.cart.tootip 키에 매핑된 메시지를 가져옴
        * 2. 1에서 가져온 메시지에 포함된 변수는 args 배열 값과 교체됨
        *    여기에서는 배열 크기가 1, 첫 번째 값은 "10"이므로 "10"이 메시지의 첫 번째 변수에 들어감
        * 3. 해당 메시지는 errorMessage 변수에 저장됨
        * */
        String errorMessage = messageSource.getMessage("main.cart.tooltip", args, locale);
        BadRequestException badRequestException = new BadRequestException(errorMessage);

        LocalDate errorDate = LocalDate.now();
        log.trace("trace log at, {}", errorDate);
        log.debug("debug log at, {}", errorDate);
        log.info("info log at, {}", errorDate);
        log.warn("warn log at, {}", errorDate);
        log.error("error log at, {}, {}", errorDate, "errorMessage", badRequestException);

        // 예외 상황을 가정한 코드이기 때문에 badRequestException 발생시키기
        throw badRequestException;
    }
}
