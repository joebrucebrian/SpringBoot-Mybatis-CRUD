package com.lty.springbootmybatiscrud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(value = "com.lty.springbootmybatiscrud.dao")
@SpringBootApplication
public class SpringBootMybatisCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisCrudApplication.class, args);
    }

}
