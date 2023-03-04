package com.msa.example.chapter10.adapter.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Slf4j
public class CacheAdapter {
    private final RedisTemplate<HotelCacheKey, HotelCacheValue> hotelCacheRedisTemplate;
    private final ValueOperations<HotelCacheKey, HotelCacheValue> hotelCacheOperation;

    public CacheAdapter(RedisTemplate<HotelCacheKey, HotelCacheValue> hotelCacheRedisTemplate) {
        this.hotelCacheRedisTemplate = hotelCacheRedisTemplate;
        /*
        * CacheAdapter 클래스는 레디스의 키-밸류 자료 구조를 사용
        * -> RedisTemplate의 opsForValue() 메소드로 ValueOperations 객체 생성
        * */
        this.hotelCacheOperation = hotelCacheRedisTemplate.opsForValue();
    }

    public void put(HotelCacheKey key, HotelCacheValue value) {
        // 레디스 데이터 유효기간으 24시간으로 설정
        hotelCacheOperation.set(key, value, Duration.ofSeconds(24*60*60));
    }
}
