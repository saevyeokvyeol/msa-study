package com.msa.example.chapter12.event.user;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/*
* 사용자를 생성하거나 삭제할 때 게시하는 이벤트 메시지 클래스
* -> ApplicationEvent를 상속
* */
@Getter
public class UserEvent extends ApplicationEvent {
    private Type type;
    private Long userId;
    private String emailAddress;

    private UserEvent(Object source, Type type, Long userId, String emailAddress) {
        /*
        * source: ApplicationEvent에서 이벤트를 게시하는 클래스 객체
        *         UserService 클래스에서 UserEvent 객체를 생성하고 게시한다면 UserService 객체를 source로 넘김
        * */
        super(source);
        this.type = type;
        this.userId = userId;
        this.emailAddress = emailAddress;
    }

    /*
    * 클래스 정적 팩토리 메서드로 파라미터를 받아 내부 생성자 호출
    * 이 때 생성 시 열거형 상수인 Type.CREATE를 사용해 사용자 생성임을 명시
    * */
    public static UserEvent created(Object source, Long userId, String emailAddress) {
        return new UserEvent(source, Type.CREATE, userId, emailAddress);
    }

    /*
    * UserEvent 타입을 의미하는 열거형
    * 사용자 생성을 뜻하는 CREATE와 사용자 삭제를 뜻하는 DELETE를 할당해 사용
    * */
    public enum Type {
        CREATE, DELETE
    }
}
