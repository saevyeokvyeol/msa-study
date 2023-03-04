package com.msa.example.chapter10.adapter.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Slf4j
public class EventListener implements MessageListener {

    private RedisTemplate<String, String> eventRedisTemplate;
    private RedisSerializer<EventMessage> valueSerializer;

    public EventListener(RedisTemplate<String, String> eventRedisTemplate) {
        /*
        * MessageListener에서 수신한 Message 객체는 byte[] 타입 로우 데이터를 리턴함
        * 이를 자바 객체로 변환하기 위해 RedisSerializer 사용
        * -> 예제에서는 eventRedisTemplate의 valueSerializer 참조
        * */
        this.eventRedisTemplate = eventRedisTemplate;
        this.valueSerializer = (RedisSerializer<EventMessage>) eventRedisTemplate.getValueSerializer();
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        /*
        * 레디스에서 구독한 메시지 객체인 Message의 getBody() 메소드는 게시자가 토픽에 게시한 메시지 응답
        * -> valueSerializer의 deserializer() 메소드로 EventMessage 변환 가능
        * */
        EventMessage eventMessage = valueSerializer.deserialize(message.getBody());

        /*
        * Message의 getChannel() 메소드는 byte[] 타입 리턴 -> new String()을 통해 문자열로 변혼
        * */
        log.warn("Subscribe Channel : {}", new String(message.getChannel()));
        log.warn("Subscribe Message : {}", eventMessage.toString());
    }
}