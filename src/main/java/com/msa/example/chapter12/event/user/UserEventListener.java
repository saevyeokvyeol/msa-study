package com.msa.example.chapter12.event.user;

import com.msa.example.chapter12.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
// 이벤트를 구독하는 클래스는 ApplicationListener을 구현하고 제네릭 클래스 타입으로 구독할 이벤트 클래스 타입을 지정해 개발
public class UserEventListener implements ApplicationListener<UserEvent> {

    /*
    * UserEvent 이벤트 객체 타입에 따라 기능을 개발할 EventService 스프링 빈 주입
    * -> UserEventListener는 UserEvent를 구독해 실제 이벤트 처리는 별도 클래스에 위임
    * */
    private final EventService eventService;

    public UserEventListener(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void onApplicationEvent(UserEvent event) {
        if (UserEvent.Type.CREATE == event.getType()) {
            log.info("Listen CREATE event. {}, {}", event.getUserId(), event.getEmailAddress());
            // UserEvent.Type.CREATE일 때 sendEventMail() 메소드 호출( == 메일 발신)
            eventService.sendEventMail(event.getEmailAddress());
        } else if (UserEvent.Type.DELETE == event.getType()) {
            log.info("Listen DELETE event.");
        } else {
            log.error("Unsupported event type. {}", event.getType());
        }
    }
}
