
package com.microne.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author machaojin
 * 
 * @email 1917939763@qq.com
 * 
 */
@MapperScan("com.microne.mall.dao")
@SpringBootApplication
@EnableTransactionManagement
public class MicroneMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroneMallApplication.class, args);
    }
}
