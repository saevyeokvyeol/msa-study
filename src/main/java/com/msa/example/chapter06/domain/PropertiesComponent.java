package com.msa.example.chapter06.domain;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public class PropertiesComponent {
    private final List<String> bootStrapServers;
    private final Integer ackLevel;

    /*
    * @Value
    * : application.properties 파일에 정의된 데이터를 스프링 빈에 주입하기 위한 어노테이션
    *   클래스 필드, 메소드, 파라미터에 주입 가능하며, 메소드에 정의할 때에는 해당 메소드는 setter 패턴으로 정의되어야 함
    *   ${""} 따옴표 안에 프로퍼티 키 값을 넣으면 밸류가 주입됨
    * */
    public PropertiesComponent(// 콤마로 구분된 프로퍼티 값은 리스트 형태로 변경해 저장 가능
                               @Value("${springtour.kafka.bootstrap-servers}") List<String> bootStrapServers,
                               // 문자열 데이터를 바인딩하는 과정에서 형 변환 가능
                               @Value("${springtour.kafka.ack-level}") Integer ackLevel) {
        this.bootStrapServers = bootStrapServers;
        this.ackLevel = ackLevel;
    }
}
