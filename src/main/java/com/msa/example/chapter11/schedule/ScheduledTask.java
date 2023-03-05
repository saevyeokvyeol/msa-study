package com.msa.example.chapter11.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTask {
    /*
    * @Scheduled 어노테이션의 fixedRate 속성 설정
    * -> fixedRate에 설정된 시간 간격(ms)으로 어노테이션이 사용된 메소드가 스케줄링 태스크로 실행됨
    * */
    @Scheduled(fixedRate = 1000L)
    public void triggerEvent() {
        log.info("Triggered Event");
    }
}
