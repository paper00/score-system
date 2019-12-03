package com.score;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author paper
 * @date 2019/11/03
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
@MapperScan(basePackages = "com.score.mapper")
public class ScoreSystemApplication {
	public static void main(String[] args) {
        SpringApplication.run(ScoreSystemApplication.class, args);
    }
}
