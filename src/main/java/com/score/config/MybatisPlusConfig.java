package com.score.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * @author paper
 * @date 2019/11/14
 * @description TODO
 */
@Configuration
@MapperScan("com.score.mapper")
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisPlusConfig {
	/**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
