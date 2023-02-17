package com.msa.example.chapter06.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "springtour.kafka")
public class KafkaProperties {
    private List<String> bootstrapServers;
    private Integer ackLevel;
    private org.springframework.boot.autoconfigure.kafka.KafkaProperties.Retry.Topic topic;

}
