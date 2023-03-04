package com.msa.example.chapter10.config;

import com.msa.example.chapter10.adapter.cache.HotelCacheKey;
import com.msa.example.chapter10.adapter.cache.HotelCacheKeySerializer;
import com.msa.example.chapter10.adapter.cache.HotelCacheValue;
import com.msa.example.chapter10.adapter.cache.HotelCacheValueSerializer;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;


@Slf4j
@Configuration
public class CacheConfig {
    /*
    * RedisConnectionFactory를 사용한 예제
    * RedisConnectionFactory: 단독으로 구성된 레디스 서버에 커넥션을 맺을 때 사용
    * */
    @Bean
    public RedisConnectionFactory cacheRedisConnectionFactory() {
        /*
        * 레디스 서버의 IP 수조와 포트 설정
        * 파라미터로 입력한 6379는 레디스 기본 포트
        * */
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
        /*
        * 레디스 데이터베이스 번호 설정
        * 레디스 서버는 내부에 16개의 데이터베이스 구분 운영 가능하며, 이를 각 컴포넌트에 할당 운영해 장비를 효율적으로 사용할 수 있음
        * */
        configuration.setDatabase(0);
        // 레디스에 접속할 수 있는 이름과 암호 설정
        configuration.setUsername("username");
        configuration.setPassword("password");

        // 레디스와 클라이언트 사이 커넥션 생성 최대 시간 설정
        final SocketOptions socketOptions = SocketOptions.builder()
            .connectTimeout(Duration.ofSeconds(10)).build();
        final ClientOptions clientOptions = ClientOptions.builder()
            .socketOptions(socketOptions).build();

        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
            .clientOptions(clientOptions)
            // 커넥션 생성 후 레디스 명령어 실행-응답 시간 설정
            .commandTimeout(Duration.ofSeconds(5))
            // 레디스 클라이언트를 안전하게 종료하기 위해 어플리케이션 종료 시까지의 최대 대기 시간
            .shutdownTimeout(Duration.ZERO)
            .build();

        return new LettuceConnectionFactory(configuration, lettuceClientConfiguration);
    }

    /*
    * RedisSentinelConfiguration을 이용한 레디스 센티넬 구성 설정
    * RedisSentinelConfiguration: 레디스 센티넬 구조로 구성된 레디스 센티넬 서버에 커넥션을 맺을 때 사용
    * */
    @Bean
    public RedisConnectionFactory cacheRedisConnectionFactoryByRedisSentinelConfiguration() {
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
        // 레디스 센티넬이 모니터링할 레디스 서버 중 마스터 서버 이름 설정
        configuration.setMaster("REDIS_MASTER_NAME");

        // 센티넬 서버 추가
        configuration.sentinel("127.0.0.1", 19999);
        configuration.sentinel("127.0.0.1", 19998);
        configuration.sentinel("127.0.0.1", 19997);

        // 센티넬 암호 설정
        configuration.setPassword("password");

        return new LettuceConnectionFactory(configuration);
    }

    /*
     * RedisClusterConfiguration을 이용한 레디스 센티넬 구성 설정
     * RedisClusterConfiguration: 레디스 센티넬 구조로 구성된 레디스 센티넬 서버에 커넥션을 맺을 때 사용
     * */
    @Bean
    public RedisConnectionFactory cacheRedisConnectionFactoryByRedisClusterConfiguration() {
        RedisClusterConfiguration configuration = new RedisClusterConfiguration();
        // 최대 리다이렉션 횟수 설정
        configuration.setMaxRedirects(3);

        /*
        * 레디스 클러스터에 포함된 레디스 서버 아이피 주소와 포트 설정
        * 클러스터에 포함된 일부 노드 정보만 입력해도 클러스터의 모든 정보가 클라이언트에 동기화됨
        * */
        configuration.setClusterNodes(List.of(
            new RedisNode("127.0.0.1", 19999),
            new RedisNode("127.0.0.1", 19998),
            new RedisNode("127.0.0.1", 19997)
        ));
        return new LettuceConnectionFactory(configuration);
    }

    @Bean(name="hotelCacheRedisTemplate")
    public RedisTemplate<HotelCacheKey, HotelCacheValue> hotelCacheRedisTemplate() {
        // RedisTemplate은 제네릭 타입 K, V 설정 가능
        RedisTemplate<HotelCacheKey, HotelCacheValue> hotelCacheRedisTemplate = new RedisTemplate<HotelCacheKey, HotelCacheValue>();

        // 위에서 생성한 RedisConnectionFactory 스프링 빈을 setConnectionFactory() 메소드 파라미터로 입력해 RedisTemplate 객체에 설정
        hotelCacheRedisTemplate.setConnectionFactory(cacheRedisConnectionFactory());

        // 키와 밸류를 직렬화/역직렬화할 수 있는 RedisSerializer 구현체 설정
        hotelCacheRedisTemplate.setKeySerializer(new HotelCacheKeySerializer());
        hotelCacheRedisTemplate.setValueSerializer(new HotelCacheValueSerializer());

        /*
        * Hashs 자료구조를 설정할 경우 setValueSerializer() 메소드 설정을 지우고 아래 설정 추가
        * */
        // hotelCacheRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hotelCacheRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<HotelCacheValue>(HotelCacheValue.class));
        return hotelCacheRedisTemplate;
    }


}
