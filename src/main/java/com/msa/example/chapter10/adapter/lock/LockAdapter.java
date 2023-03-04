package com.msa.example.chapter10.adapter.lock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

public class LockAdapter {
    private final RedisTemplate<LockKey, Long> lockRedisTemplate;
    private final ValueOperations<LockKey, Long> lockOperation;

    public LockAdapter(RedisTemplate<LockKey, Long> lockRedisTemplate) {
        this.lockRedisTemplate = lockRedisTemplate;
        this.lockOperation = lockRedisTemplate.opsForValue();
    }

    /*
    * 레디스에 락을 생성하는 메소드
    * 레디스 키에 hotelId를 사용해 LockKey 객체를 생성해 저장하고 레디스 밸류에 userId를 저장
    * */
    public Boolean holdLock(Long hotelId, Long userId) {
        LockKey lockKey = LockKey.from(hotelId);

        /*
        * setIfAbsent() 메소드는 레디스에 키와 매핑되는 값이 없을 때만 데이터 생성
        * 이 때 데이터를 생성하면 Boolean.TRUE, 데이터를 생성하지 못하면 Boolean.FALSE 리턴
        * -> Boolean.FALSE가 리턴될 경우 레디스에 데이터가 있음
        *
        * 세 번째 파라미터는 레디스 유효 기간 설정
        * */
        return lockOperation.setIfAbsent(lockKey, userId, Duration.ofSeconds(10));
    }

    // 레디스에 락이 있는지 확인
    public Long checkLock(Long hotelId) {
        LockKey lockKey = LockKey.from(hotelId);
        return lockOperation.get(lockKey);
    }

    // 레디스 락을 삭제
    public void clearLock(Long hotelId) {
        lockRedisTemplate.delete(LockKey.from(hotelId));
    }
}
