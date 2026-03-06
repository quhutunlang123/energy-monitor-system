package com.simple.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.simple.test.mapper")
public class SimpleTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleTestApplication.class, args);
    }
}
