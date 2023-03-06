package com.msa.example.chapter12.event.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventPublisher {
    // ApplicationContext에 이벤트를 게시하는 ApplicationEventPublisher를 주입받음(스프링 프레임워크 제공)
    private final ApplicationEventPublisher applicationEventPublisher;

    public UserEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishUserCreated(Long userId, String emailAddress) {
        // 이벤트 메시지 UserEvent 객체 생성
        UserEvent userEvent = UserEvent.created(this, userId, emailAddress);
        log.info("Publish user created event.");
        // ApplicationEventPublisher의 publishEvent() 메소드로 UserEvent 이벤트 메시지 게시
        applicationEventPublisher.publishEvent(userEvent);
    }
}
