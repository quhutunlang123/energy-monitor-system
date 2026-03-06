package com.energy.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.energy.core.mapper")
public class MyBatisPlusConfig {

}

