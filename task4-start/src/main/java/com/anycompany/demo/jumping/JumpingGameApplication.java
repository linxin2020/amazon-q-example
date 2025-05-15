package com.anycompany.demo.jumping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 跳跳棋活动系统主启动类
 */
@SpringBootApplication
@MapperScan("com.anycompany.demo.jumping.mapper")
public class JumpingGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(JumpingGameApplication.class, args);
    }
} 