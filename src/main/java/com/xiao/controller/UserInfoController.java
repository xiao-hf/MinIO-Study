package com.xiao.controller;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xiao.common.R;
import com.xiao.config.MinioInfo;
import com.xiao.entity.UserInfo;
import com.xiao.service.UserInfoService;
import com.xiao.utils.MinioUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin // 支持跨域访问
@RestController
public class UserInfoController {

    @Resource
    private MinioInfo minioInfo;

    @Resource
    private MinioUtil minioUtil;

    @Resource
    private UserInfoService userInfoService;

    @GetMapping(value = "/api/users")
    public R users() {
        List<UserInfo> users = userInfoService.list();
        return R.success(users);
    }

    @PostMapping(value = "/api/user/image")
    public R image(MultipartFile file) throws Exception {
        minioUtil.uploadFile(minioInfo.getBucket(), file);
        return R.success();
    }

    @PostMapping(value = "/api/user/contract")
    public R contract(MultipartFile file) throws Exception {
        minioUtil.uploadFile(minioInfo.getBucket(), file);
        return R.success();
    }

    @GetMapping("/api/user/{id}")
    public R user(@PathVariable Integer id) {
        UserInfo userInfo = userInfoService.getUserById(id);
        return R.success(userInfo);
    }

}
