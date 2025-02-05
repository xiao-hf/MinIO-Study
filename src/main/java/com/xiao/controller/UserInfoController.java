package com.xiao.controller;

import com.mysql.cj.Constants;
import com.xiao.common.R;
import com.xiao.config.MinioInfo;
import com.xiao.entity.UserInfo;
import com.xiao.service.UserContractService;
import com.xiao.service.UserImageService;
import com.xiao.service.UserInfoService;
import com.xiao.utils.MinioUtil;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin // 支持跨域访问
@RestController
@RequestMapping("api")
public class UserInfoController {

    @Resource
    MinioClient minioClient;

    @Resource
    private UserImageService userImageService;

    @Resource
    private UserContractService userContractService;

    @Resource
    private MinioInfo minioInfo;

    @Resource
    private MinioUtil minioUtil;

    @Resource
    private UserInfoService userInfoService;

    @DeleteMapping("delUser/{id}")
    public R delUser(@PathVariable Integer id) {
        return userInfoService.delUser(id) ? R.success() : R.fail();
    }

    @GetMapping("download/{id}")
    public void download(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        UserInfo userInfo = userInfoService.getUserById(id);
        String bucket = userInfo.getUserContractDO().getBucket();
        String object = userInfo.getUserContractDO().getObject();

        // 要想让浏览器弹出下载框, 后端要设置以下参数
        response.setContentType("application/octet-stream"); // 二进制流
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(object, StandardCharsets.UTF_8));

        GetObjectResponse resp = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build());

        resp.transferTo(response.getOutputStream());
    }

    @GetMapping(value = "users")
    public R users() {
        List<UserInfo> users = userInfoService.getUserList();
        return R.success(users);
    }

    @PostMapping(value = "user/image")
    public R image(MultipartFile file, @RequestParam(value = "id") Integer uid) throws Exception {
        String name = file.getOriginalFilename();
        String object = uid + name.substring(name.indexOf('.'));
        minioUtil.uploadFile(minioInfo.getBucket(), object, file);
        userImageService.saveOrUpdateImage(uid, minioInfo.getBucket(), object);
        return R.success();
    }

    @PostMapping(value = "user/contract")
    public R contract(MultipartFile file, @RequestParam(value = "id") Integer uid) throws Exception {
        String name = file.getOriginalFilename();
        String object = uid + name.substring(name.indexOf('.'));
        minioUtil.uploadFile(minioInfo.getBucket(), object, file);
        userContractService.saveOrUpdateImage(uid, minioInfo.getBucket(), object);
        return R.success();
    }

    @GetMapping("user/{id}")
    public R user(@PathVariable Integer id) {
        UserInfo userInfo = userInfoService.getUserById(id);
        return R.success(userInfo);
    }

    @PutMapping("user")
    public R updUser(UserInfo userInfo) throws Exception {
        return userInfoService.updateById(userInfo) ? R.success() : R.fail();
    }
}
