package com.xiao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xiao.entity.UserImage;
import com.xiao.entity.UserInfo;
import com.xiao.mapper.UserImageMapper;
import com.xiao.mapper.UserInfoMapper;
import com.xiao.service.UserImageService;
import com.xiao.service.UserInfoService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class JavaMinio1ApplicationTests {

    @Resource
    private MinioClient minioClient;

    @Resource
    private UserImageMapper userImageMapper;

    @Resource
    private UserImageService userImageService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserInfoService userInfoService;

    @Test
    void test1() {
        System.out.println(userInfoService.getUserList());
    }

    @Test
    void generatorUser() {
        for (int i = 1; i <= 10; i++) {
            Date now = new Date();
            UserInfo userInfo = new UserInfo();
            userInfo.setId(i);
            userInfo.setSex(1);
            userInfo.setNick("11");
            userInfo.setPassword("11");
            userInfo.setAddress("111");
            userInfo.setEmail("111");
            userInfo.setPhone("1111");
            userInfo.setCreateTime(now);
            userInfo.setUpdateTime(now);
            userInfoMapper.insert(userInfo);
        }
    }

    @Test
    void test() {
//        UserImage one = userImageService.lambdaQuery().eq(UserImage::getUid, 1).one();
//        System.out.println(one);
        LambdaQueryWrapper<UserImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserImage::getUid, 1);
        int delete = userImageMapper.delete(wrapper);
        System.out.println(delete);
    }

    @Test
    void main() throws Exception {
//        listObjects("myfile");
//        removeObject("myfile", "幻境");
//        removeBucket("userbucket");
//        createBucket("user-bucket");
//        createBucket("userbucket");
        LambdaQueryChainWrapper<UserImage> wrapper = new LambdaQueryChainWrapper<>(userImageMapper);
        UserImage userImage = wrapper.eq(UserImage::getUid, "1").one();
        System.out.println(userImage);
    }

    @Test
    void removeObject(String buckName, String objName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(buckName)
                .object(objName)
                .build());
    }

    @Test
    void listObjects(String bucketName) {
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

    @Test
    void transfer(String bucketName, String objName, String targetDir) throws Exception {
        minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objName)
                .build())
                .transferTo(new FileOutputStream(targetDir));
    }

    @Test
    byte[] getFile(String bucketName, String objName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objName)
                .build()).readAllBytes();
    }

    @Test
    String getObjUrl(String bucketName, String ObjName, Method method) throws Exception {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(ObjName)
                .expiry(3, TimeUnit.MINUTES) // 指定过期时间
                .method(method) // 指定访问方式 GET等
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
        ObjectWriteResponse resp = minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objName)
                .stream(fis, file.length(), -1)
                .build());
        fis.close();
        log.info("{}", resp.versionId());
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
