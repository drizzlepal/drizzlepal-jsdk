package com.drizzlepal.springboot.miniostarter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.drizzlepal.springboot.miniostarter.props.DrizzlepalMinioStarterProps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(DrizzlepalMinioStarterProps.class)
@ComponentScan("com.drizzlepal.springboot.miniostarter")
public class MinioStarterConfig {

    @Bean
    public MinioTemplate minioTemplate(DrizzlepalMinioStarterProps props) {
        log.info("init minioTemplate");
        return new MinioTemplate(props);
    }

}
