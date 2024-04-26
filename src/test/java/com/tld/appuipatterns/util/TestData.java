package com.tld.appuipatterns.util;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;


public class TestData {

    public static String getRandomOrderId(){
        return String.valueOf(Faker.instance().number().numberBetween (1, 999));
    }

    public static String getRandomUsername(){
        return RandomStringUtils.randomAlphabetic(2);
    }

    public static String getRandomPassword(){
        return RandomStringUtils.randomAlphabetic(8);
    }

    public static String getToken(){
        return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5paWx2IiwiZXhwIjoxNzEzNTU5MTM5LCJpYXQiOjE3MTM1NDExMzl9.rVqU9hzT4cH8tzFdYPfo2ps5Ov8EJ62ExgVFqPmzFfU2doMzNf6JeFdBxrJy4aTQq2ExmQtEB6iABJ8dWkYMVg";
    }
}
