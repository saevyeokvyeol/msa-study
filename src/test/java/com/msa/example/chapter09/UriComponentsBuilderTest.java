package com.msa.example.chapter09;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriComponentsBuilderTest {
    @Test
    public void testBuild() {
        //                                      첫 번째 변수
        URI uri = UriComponentsBuilder.fromPath("/hotel-names/{hotelName}")
            //                 두 번째 변수                                  세 번째 변수
            .queryParam("type", "{type}").queryParam("isActive", "{isActive}")
            .scheme("https").host("127.0.0.1").port(18888)
            // build() 메소드의 파라미터가 순서대로 변수에 입력
            .build("LineHotel", "Hotel", "true");

        //                      uri 변수에 생성된 URI와 입력한 URI가 동일한지 검증
        Assertions.assertEquals("https://127.0.0.1:18888/hotel-names/LineHotel?type=Hotel&isActive=true", uri.toString());
    }

    @Test
    public void testEncoding() {
        // "/hotel-names/{hotelName}"에 "한국호텔"을 삽입해 UriComponentsBuilder 객체 생성(인코딩 진행)
        URI firstUri = UriComponentsBuilder.fromPath("/hotel-names/{hotelName}")
            .scheme("https").host("127.0.0.1").port(18888)
            .build("한국호텔");

        // 템플릿 변수가 퍼센트로 인코딩된 URI로 비교
        Assertions.assertEquals("https://127.0.0.1:18888/hotel-names/%ED%95%9C%EA%B5%AD%ED%98%B8%ED%85%94", firstUri.toString());

        // 파라미터로 variable를 사용해 path 생성(템플릿 변수 X)
        String variable = "한국호텔";
        String path = "/hotel-names/" + variable;
        URI secondeUri = UriComponentsBuilder.fromPath(path)
            .scheme("https").host("127.0.0.1").port(18888)
            // 템플릿 변수를 사용하지 않았기 때문에 파라미터 X
            .build()
            // URI 객체로 변환
            .toUri();

        // 인코딩되지 않았기 때문에 한글 그대로 비교
        Assertions.assertEquals("https://127.0.0.1:18888/hotel-names/한국호텔", secondeUri.toString());

        URI thirdUri = UriComponentsBuilder.fromPath(path)
            .scheme("https").host("127.0.0.1").port(18888)
            // build() 메소드 변수로 인코딩 여부를 알려준 뒤 인코딩 진행
            .build(false).encode()
            .toUri();

        // UriComponentsBuilder에서 인코딩을 진행했으므로 퍼센트 인코딩된 URI로 비교
        Assertions.assertEquals("https://127.0.0.1:18888/hotel-names/%ED%95%9C%EA%B5%AD%ED%98%B8%ED%85%94", thirdUri.toString());
    }
}
