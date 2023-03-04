package com.msa.example.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public void testMethod() {
        System.out.println("testMethod() 호출");
    }
}
