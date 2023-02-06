package com.msa.example.chapter05.util;


import java.util.Random;

public class IdGenerator {

    private static final Integer bound = 10000;

    public static Long create() {
        Random random = new Random();
        return System.currentTimeMillis() * bound + random.nextInt(bound);
    }
}