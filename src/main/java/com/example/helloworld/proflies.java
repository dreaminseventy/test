package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

public class proflies {
    @SpringBootApplication
    public class ProfilesApplication {
        public static void main(String[] args) {
            SpringApplication.run(ProfilesApplication.class, args);
        }
    }
    @Configuration
    @Profile("development")
    class DevelopmentConfig {
        @Bean
        public DataSource dataSource() {
            System.out.println("设置开发环境的DataSource配置");
            return new DevelopmentDataSource();
        }
    }
    @Configuration
    @Profile("production")
    class ProductionConfig {
        @Bean
        public DataSource dataSource() {
            System.out.println("设置生产环境的DataSource配置");
            return new ProductionDataSource();
        }
    }
    interface DataSource {
        // 数据源接口
    }
    class DevelopmentDataSource implements DataSource {
        // 开发环境的数据源实现
    }
    class ProductionDataSource implements DataSource {
        // 生产环境的数据源实现
    }
}
