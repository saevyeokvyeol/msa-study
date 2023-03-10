package com.msa.example.chapter10.adapter.rank;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BiddingAdapter {

    private String PREFIX = "HOTEL-BIDDING::";
    private RedisTemplate<String, Long> biddingRedisTemplate;

    public BiddingAdapter(RedisTemplate<String, Long> biddingRedisTemplate) {
        this.biddingRedisTemplate = biddingRedisTemplate;
    }

    /*
    * 별도의 RedisSerializer 구현체를 생성하지 않는 방법
    * 이벤트에 참가하는 호텔 아이디를 사용해 Sorted Key 생성
    * */
    private String serializeKey(Long hotelId) {
        return new StringBuilder(PREFIX).append(hotelId).toString();
    }

    public Boolean createBidding(Long hotelId, Long userId, Double amount) {
        String key = this.serializeKey(hotelId);
        //
        return biddingRedisTemplate.opsForZSet().add(key, userId, amount);
    }

    public List<Long> getTopBidders(Long hotelId, Integer fetchCount) {
        String key = this.serializeKey(hotelId);
        return biddingRedisTemplate
            .opsForZSet()
            .reverseRangeByScore(key, 0D, Double.MAX_VALUE, 0, fetchCount)
            .stream()
            .collect(Collectors.toList());
    }

    public Double getBidAmount(Long hotelId, Long userId) {
        String key = this.serializeKey(hotelId);
        return biddingRedisTemplate.opsForZSet().score(key, userId);
    }

    public void clear(Long hotelId) {
        String key = this.serializeKey(hotelId);
        biddingRedisTemplate.delete(key);
    }
}
