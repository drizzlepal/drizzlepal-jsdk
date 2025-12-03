package com.drizzlepal.springboot.miniostarter.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "drizzlepal.minio")
public class DrizzlepalMinioStarterProps {

    /*
     * minio 服务地址
     */
    private String endpoint;

    /**
     * minio 访问的 accessKey
     */
    private String accessKey;

    /**
     * minio 访问的 secretKey
     */
    private String secretKey;

}
