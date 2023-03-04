package com.msa.example.chapter10.adapter.lock;

import java.util.Objects;

public class LockKey {
    private static final String PREFIX = "LOCK::";

    private Long eventHotelId;

    private LockKey(Long eventHotelId) {
        if (Objects.isNull(eventHotelId)) throw new IllegalArgumentException("eventHotelId can't be null");
        this.eventHotelId = eventHotelId;
    }

    public static LockKey from(Long eventHotelId) {
        return new LockKey(eventHotelId);
    }

    /*
    * LockKey 객체를 레디스 키로 저장할 때 직렬화하는 메소드
    * 'LOCK:: + eventHotelId'로 직렬화되어 저장
    * */
    @Override
    public String toString() {
        return new StringBuilder(PREFIX).append(eventHotelId).toString();
    }

    // 레디스에 저장된 키를 LockKey 객체로 역직렬화하는 코드
    public static LockKey fromString(String key) {
        String idToken = key.substring(0, PREFIX.length());
        Long eventHotelId = Long.valueOf(idToken);

        return LockKey.from(eventHotelId);
    }
}
