package com.msa.example.chapter10.adapter.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
public class HotelCacheValueSerializer implements RedisSerializer<HotelCacheValue> {
    //JSON Mapper
    /*
    * HotelCacheValue 객체를 직렬화한 메시지 포맷은 JSON
    * 때문에 JSON 메시지 변환에 사용할 ObjectMapper 객체를 생성함
    * 이 때 ObjectMapper는 생성 비용이 비싸고 멀티 스레드 환경에 안전하기 때문에 상수로 생성해 공유
    * */
    public static final ObjectMapper MAPPER = new ObjectMapper();
    private final Charset UTF_8 = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(HotelCacheValue hotelCacheValue) throws SerializationException {
        if (Objects.isNull(hotelCacheValue)) return null;

        try {
            // MAPPER의 writeValueAsString() 메소드로 hotelCacheValue -> JSON으로 변환
            String json = MAPPER.writeValueAsString(hotelCacheValue);
            return json.getBytes(UTF_8);
        } catch (JsonProcessingException e) {
            throw new SerializationException("json serialize error", e);
        }
    }

    @Override
    public HotelCacheValue deserialize(byte[] bytes) throws SerializationException {
        // 레디스 밸류를 역직렬화할 때 키와 맞는 밸류가 없는 경우도 있으므로 null일 경우 무조건 예외 던지기 X
        if (Objects.isNull(bytes)) return null;

        try {
            // MAPPER의 readValue() 메소드로 HotelCacheValue -> JSON으로 변환
            return MAPPER.readValue(new String(bytes, UTF_8), HotelCacheValue.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("json serialize error", e);
        }
    }
}
