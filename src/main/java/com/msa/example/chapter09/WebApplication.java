package com.msa.example.chapter09;

import com.msa.example.chapter09.adapter.BillingAdapter;
import com.msa.example.chapter09.controller.BillingCodeResponse;
import com.msa.example.chapter09.controller.CreateCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.module.Configuration;
import java.util.List;

@Slf4j
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);

        BillingAdapter billingAdapter = context.getBean(BillingAdapter.class);

        /*
        * BillingAdapter의 getBillingCodes() 메소드는 RestTemplate의 getForEntity() 메소드로 GET /billing-codes API를 실행
        * */
        List<BillingCodeResponse> responses = billingAdapter.getBillingCodes("CODE:1231231");
        log.info("1. Result : {}", responses);

        /*
        * BillingAdapter의 create() 메소드는 RestTemplate의 postForEntity() 메소드로 POST /billing-codes API를 실행함
        * */
        CreateCodeResponse createCodeResponse = billingAdapter.create(List.of(1231231L));
        log.info("2. Result : {}", createCodeResponse);

        /*
        * BillingAdapter의 createBillingCode() 메소드는 RestTemplate의 exchange() 메소드로 POST /billing-codes API를 실행
        * */
        CreateCodeResponse codeResponse = billingAdapter.createBillingCode(List.of(9000L, 8000L, 7000L));
        log.info("3. Result : {}", codeResponse);
    }
}
