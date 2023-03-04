package com.msa.example.chapter10.config;

import com.msa.example.chapter10.service.HotelKeyGenerator;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableCaching
@Configuration
public class BasicCacheConfig {

    @Bean
    public RedisConnectionFactory basicCacheRedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("127.0.0.1", 6379);

        final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(10)).build();
        final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();

        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
            .clientOptions(clientOptions)
            .commandTimeout(Duration.ofSeconds(5))
            .shutdownTimeout(Duration.ZERO)
            .build();

        return new LettuceConnectionFactory(configuration, lettuceClientConfiguration);
    }

    @Bean
    public CacheManager cacheManager() {
        /*
        * RedisCacheConfiguration: 캐시 데이터를 저장하는 RedisCache 설정 기능 제공
        * defaultCacheConfig() 메소드는 기본값으로 설정된 RedisCacheConfiguration 객체 응답
        * */
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
            // 캐시 데이터 유효 기간 설정
            .entryTtl(Duration.ofHours(1))
            // 캐시 데이터 키를 직렬화할 때 StringRedisSerializer 사용 == 문자열로 변환해 저장
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            // 캐시 데이터 값을 직렬화할 때 GenericJackson2JsonRedisSerializer을 사용해 JSON 메시지로 저장
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer(Object.class)));

        // HashMap 타입 configurations 객체는 키-캐시 이름, 밸류-캐시 설정용 RedisCacheConfiguration 저장
        Map<String, RedisCacheConfiguration> configurations = new HashMap<>();
        /*
        * hotelCache 캐시는 캐시 데이터 유효 기간을 30분으로 변경
        * 이 때 RedisCacheConfiguration는 불변 객체이기 때문에 entryTtl()는 새로운 RedisCacheConfiguration를 리턴하므로 기존 객체에 영향 X
        * */
        configurations.put("hotelCache", defaultConfig.entryTtl(Duration.ofMinutes(30)));
        // hotelAddressCache는 유효 기간을 하루로 변경
        configurations.put("hotelAddressCache", defaultConfig.entryTtl(Duration.ofDays(1)));

        return RedisCacheManager.RedisCacheManagerBuilder
            // RedisCacheManager가 사용할 RedisConnectionFactory 객체 설정
            .fromConnectionFactory(basicCacheRedisConnectionFactory())
            // RedisCacheConfiguration를 파라미터로 받아 RedisCacheManeger 기본 캐시로 설정
            .cacheDefaults(defaultConfig)
            // RedisCacheManager를 생성할 때 초기값 설정
            .withInitialCacheConfigurations(configurations)
            .build();
    }

    @Bean
    public HotelKeyGenerator hotelKeyGenerator() {
        return new HotelKeyGenerator();
    }
}