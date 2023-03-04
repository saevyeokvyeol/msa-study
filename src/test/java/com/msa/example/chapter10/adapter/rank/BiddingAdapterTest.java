package com.msa.example.chapter10.adapter.rank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BiddingAdapterTest {

    private final Long firstUserId = 1L;
    private final Long secondUserId = 2L;
    private final Long thirdUserId = 3L;
    private final Long fourthUserId = 4L;
    private final Long fifthUserId = 5L;
    private final Long hotelId = 1000L;

    @Autowired
    private BiddingAdapter biddingAdapter;

    @Test
    public void simulate() {
        biddingAdapter.clear(hotelId);

        // 100d에서 140d까지 총 5명의 사용자가 각각 입찰하는 시나리오
        biddingAdapter.createBidding(hotelId, firstUserId, 100d);
        biddingAdapter.createBidding(hotelId, secondUserId, 110d);
        biddingAdapter.createBidding(hotelId, thirdUserId, 120d);
        biddingAdapter.createBidding(hotelId, fourthUserId, 130d);
        biddingAdapter.createBidding(hotelId, fifthUserId, 140d);

        // 두 번째 사용자, 첫 번째 사용자 재입찰
        biddingAdapter.createBidding(hotelId, secondUserId, 150d);
        biddingAdapter.createBidding(hotelId, firstUserId, 200d);

        // 상위 입찰자 3명 조회
        List<Long> topBidders = biddingAdapter.getTopBidders(hotelId, 3);

        // 상위 입찰자 3명이 첫 번째, 세 번째, 다섯 번째 사용자인지 확인
        Assertions.assertEquals(firstUserId, topBidders.get(0));
        Assertions.assertEquals(secondUserId, topBidders.get(1));
        Assertions.assertEquals(fifthUserId, topBidders.get(2));

        // 상위 입찰자가 입찰한 금액이 정확한지 확인
        Assertions.assertEquals(200d, biddingAdapter.getBidAmount(hotelId, firstUserId));
        Assertions.assertEquals(150d, biddingAdapter.getBidAmount(hotelId, secondUserId));
        Assertions.assertEquals(140d, biddingAdapter.getBidAmount(hotelId, fifthUserId));
    }
}
