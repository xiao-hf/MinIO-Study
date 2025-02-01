package com.xiao.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://192.168.80.128:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
