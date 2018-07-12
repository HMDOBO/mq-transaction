package com.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement    // 启动事务管理
@MapperScan("com.item.mapper")	// 指定mapper接口包位置
@PropertySource(value = {"application.properties", "jdbc.properties"})  // 指定properties文件位置
public class ItemApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ItemApplication.class, args);
        Thread.sleep(Long.MAX_VALUE);
    }
}
