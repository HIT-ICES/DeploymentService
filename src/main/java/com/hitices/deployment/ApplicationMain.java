package com.hitices.deployment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: wangteng
 * @e-mail: Willtynn@outlook.com
 * @date: 2023/8/7 17:13
 */
@EnableScheduling
@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class ApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class);
    }
}
