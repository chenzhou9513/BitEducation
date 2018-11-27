package com.bitedu.config;

import com.bitedu.common.SnowFlake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowFlakeConfig {

    private long datacenterId = 15;

    private long machineId = 5;

    @Bean(name="snowflake")
    public SnowFlake getSnowFlake(){
        return new SnowFlake(this.datacenterId,this.machineId);
    }



}
