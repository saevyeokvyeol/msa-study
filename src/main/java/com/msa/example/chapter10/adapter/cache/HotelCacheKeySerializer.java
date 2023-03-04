package com.msa.example.chapter10.adapter.cache;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.util.Objects;

public class HotelCacheKeySerializer implements RedisSerializer<HotelCacheKey> {
    private final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(HotelCacheKey hotelCacheKey) throws SerializationException {
        // 레디스 데이터 키는 null이 될 수 없으므로 검사 후 null일 경우 예외 던지기
        if (Objects.isNull(hotelCacheKey)) throw new SerializationException("hotelCacheKey is null");

        /*
        * HotelCacheKey를 직렬화해 byte[]로 리턴
        * 여기서는 Charset을 UTF_8로 설정해 byte[]로 변환
        * */
        return hotelCacheKey.toString().getBytes(UTF_8);
    }

    @Override
    public HotelCacheKey deserialize(byte[] bytes) throws SerializationException {
        if (Objects.isNull(bytes)) throw new SerializationException("bytes is null");

        // 레디스 키 데이터는 byte[]이기 때문에 변환해 리턴
        return HotelCacheKey.fromString(new String(bytes, UTF_8));
    }
}
