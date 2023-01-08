package com.peintre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Program: myblog
 * @Description: springboot启动类
 * @Author: Peintre
 * @Date: 2022/11/9 15:59
 **/
@SpringBootApplication
@MapperScan("com.peintre.dao")
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
