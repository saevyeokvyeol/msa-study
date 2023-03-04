package com.msa.example.chapter10.config;

import com.msa.example.chapter10.adapter.event.EventListener;
import com.msa.example.chapter10.adapter.event.EventMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class EventConfig {

    @Bean
    public RedisConnectionFactory eventRedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, String> eventRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(eventRedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        /*
        * JSON 포맷을 사용하기 때문에 Jackson2JsonRedisSerializer 설정
        * EventMessage.class 타입을 사용하기 때문에 Jackson2JsonRedisSerializer 제네릭 타입과 생성자 클래스 입력
        * */
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<EventMessage>(EventMessage.class));
        return redisTemplate;
    }

    @Bean
    public ChannelTopic eventTopic() {
        return new ChannelTopic("dummyTopic");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        /*
        * RedisMessageListenerContainer는 레디스와 연결하기 위해 RedisConnection 객체가 필요
        * -> setConnectionFactory() 메소드를 사용해 RedisConnection 객체를 넣어줌
        * */
        container.setConnectionFactory(eventRedisConnectionFactory());
        /*
        * RedisMessageListenerContainer에 MessageListener 구현체 등록
        * 이 때 addMessageListener 메소드는 MessageListener와 ChannelTopic을 파라미터로 받아 ChannelTopic 토픽에서 받은 메시지를 MessageListener에서 처리
        * */
        container.addMessageListener(eventListener(), eventTopic());
        return container;
    }

    @Bean
    public MessageListener eventListener(){
        return new EventListener(eventRedisTemplate());
    }
}