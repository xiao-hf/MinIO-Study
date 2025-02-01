package com.xiao;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Slf4j
@SpringBootTest
class JavaMinio1ApplicationTests {

    @Resource
    private MinioClient minioClient;

    @Test
    void main() throws Exception {
        log.info(getObjUrl("myfile", "Cat girl~", Method.GET));

//        uploadFile1("myfile", "D:\\develop\\猫娘.jpeg", "Cat girl~");
//        uploadFile2("myfile", "D:\\develop\\幻境.jpeg", "Fantasy view~");
//        StatObjectResponse resp = minioClient.statObject(StatObjectArgs.builder().bucket("myfile").object("Cat girl~").build());
//        System.out.println(resp);
    }

    @Test
    String getObjUrl(String bucketName, String ObjName, Method method) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(ObjName)
                .method(method)
                .build()
        );
    }

    @Test
    void uploadFile2(String bucketName, String dir, String fileName) throws Exception {
        ObjectWriteResponse resp = minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .filename(dir)
                .build());
        log.info("{}", resp);
    }

    @Test
    void uploadFile1(String bucketName, String dir, String objName) throws Exception {
        File file = new File(dir);
        FileInputStream fis = new FileInputStream(file);
        ObjectWriteResponse resp = minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objName)
                .stream(fis, file.length(), -1).build());
        fis.close();
        log.info("{}", resp);
    }

    @Test
    void listBuckets() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        buckets.forEach(bucket -> System.out.println(bucket.name() + "---" + bucket.creationDate()));
    }

    @Test
    void createBucket(String bucketName) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("Success!");
        } else
            log.info("Already Exist!");
    }

    @Test
    void removeBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }
}
