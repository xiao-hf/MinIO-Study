package com.xiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.entity.UserContract;
import com.xiao.entity.UserImage;
import com.xiao.entity.UserInfo;
import com.xiao.mapper.UserContractMapper;
import com.xiao.mapper.UserImageMapper;
import com.xiao.service.UserContractService;
import com.xiao.service.UserImageService;
import com.xiao.service.UserInfoService;
import com.xiao.mapper.UserInfoMapper;
import com.xiao.utils.MinioUtil;
import jakarta.annotation.Resource;
import org.apache.commons.io.input.TaggedReader;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_user_info】的数据库操作Service实现
* @createDate 2025-02-03 18:26:00
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserImageMapper userImageMapper;

    @Resource
    private UserContractMapper userContractMapper;

    @Resource
    private MinioUtil minioUtil;

    @Override
    public UserInfo getUserById(Integer id) {
        return userInfoMapper.selectUserById(id);
    }

    @Override
    public boolean delUser(Integer uid) {
        try {
            removeById(uid);

            LambdaQueryWrapper<UserImage> imageWrapper = new LambdaQueryWrapper<UserImage>().eq(UserImage::getUid, uid);
            UserImage userImage = userImageMapper.selectOne(imageWrapper);
            String bucket = userImage.getBucket();
            String object = userImage.getObject();
            if (bucket != null && object != null)
                minioUtil.removeObject(bucket, object);
            userImageMapper.delete(imageWrapper);

            LambdaQueryWrapper<UserContract> contractWrapper = new LambdaQueryWrapper<UserContract>().eq(UserContract::getUid, uid);
            UserContract userContract = userContractMapper.selectOne(contractWrapper);
            bucket = userContract.getBucket();
            object = userContract.getObject();
            if (bucket != null && object != null)
                minioUtil.removeObject(bucket, object);
            userContractMapper.delete(contractWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<UserInfo> getUserList() {
        return userInfoMapper.selectAll();
    }
}




