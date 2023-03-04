package com.msa.example.chapter10.adapter.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventPublisher {

    private final RedisTemplate<String, String> eventRedisTemplate;
    private final ChannelTopic eventTopic;

    public EventPublisher(RedisTemplate<String, String> eventRedisTemplate, ChannelTopic eventTopic) {
        this.eventRedisTemplate = eventRedisTemplate;
        this.eventTopic = eventTopic;
    }

    public void sendMessage(EventMessage eventMessage) {
        /*
        * 토픽 이름과 메시지를 파라미터로 입력받아 토픽에 메시지 전달
        * */
        eventRedisTemplate.convertAndSend(eventTopic.getTopic(), eventMessage);
    }
}