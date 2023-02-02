package com.msa.example.chapter02.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
/*
* InitializingBean에서 afterPropertiesSet(), DisposableBean에서 destroy() 메소드를 오버라이딩
* 해당 메소드를 오버라이딩하면 따로 설정하지 않아도 스프링 빈 생성과 소멸 시 메소드가 실행됨
* */
public class LifeCycleComponent implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.error("afterPropertiesSet from InitializingBean");
    }

    @Override
    public void destroy() throws Exception {
        log.error("destroy from DisposableBean");
    }

    public void init() {
        log.error("customized init method");
    }

    public void clear() {
        log.error("customized destroy method");
    }
}
