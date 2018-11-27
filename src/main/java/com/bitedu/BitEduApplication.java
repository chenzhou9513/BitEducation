package com.bitedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
//@EnableCaching
@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties
@MapperScan("com.bitedu.dao")
public class BitEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitEduApplication.class, args);
    }

}

