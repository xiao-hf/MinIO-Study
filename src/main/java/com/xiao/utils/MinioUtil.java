package com.xiao.utils;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MinioUtil {

    @Resource
    private MinioClient minioClient;

    public void removeObject(String buckName, String objName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(buckName)
                .object(objName)
                .build());
    }

    public void listObjects(String bucketName) {
        Iterable<Result<Item>> res = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        res.forEach(itemResult -> {
            try {
                Item item = itemResult.get();
                System.out.println(item.objectName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void transfer(String bucketName, String objName, String targetDir) throws Exception {
        minioClient.getObject(GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objName)
                        .build())
                .transferTo(new FileOutputStream(targetDir));
    }

    public byte[] getFile(String bucketName, String objName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objName)
                .build()).readAllBytes();
    }

    public String getObjUrl(String bucketName, String ObjName, Method method) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(ObjName)
                .expiry(3, TimeUnit.MINUTES) // 指定过期时间
                .method(method) // 指定访问方式 GET等
                .build()

        );
    }

    public void uploadFile(String bucketName, String dir, String fileName) throws Exception {
        ObjectWriteResponse resp = minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .filename(dir)
                .build());
        log.info("{}", resp);
    }

    public void uploadFile(String bucketName, MultipartFile file) throws Exception {
        ObjectWriteResponse resp = minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(file.getOriginalFilename())
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
        log.info("{}", resp.versionId());
    }

    public void listBuckets() throws Exception {
        List<Bucket> buckets = minioClient.listBuckets();
        buckets.forEach(bucket -> System.out.println(bucket.name() + "---" + bucket.creationDate()));
    }

    public void createBucket(String bucketName) throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("Success!");
        } else
            log.info("Already Exist!");
    }

    public void removeBucket(String bucketName) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }
}
