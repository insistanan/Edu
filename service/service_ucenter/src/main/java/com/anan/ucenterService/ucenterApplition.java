package com.anan.ucenterService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.anan"})
@MapperScan("com.anan.ucenterService.mapper")
public class ucenterApplition {
    public static void main(String[] args) {
        SpringApplication.run(ucenterApplition.class,args);
    }
}
