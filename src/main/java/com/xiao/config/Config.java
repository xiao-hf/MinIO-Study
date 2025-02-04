package com.xiao.config;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Resource
    MinioInfo minioInfo;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioInfo.getEndpoint())
                .credentials(minioInfo.getUsername(), minioInfo.getPassword())
                .build();
    }
}
