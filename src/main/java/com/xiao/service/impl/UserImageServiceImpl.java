package com.xiao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.entity.UserImage;
import com.xiao.service.UserImageService;
import com.xiao.mapper.UserImageMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author Administrator
* @description 针对表【t_user_image】的数据库操作Service实现
* @createDate 2025-02-03 18:25:49
*/
@Service
public class UserImageServiceImpl extends ServiceImpl<UserImageMapper, UserImage>
    implements UserImageService{

    @Resource
    UserImageMapper userImageMapper;

    @Override
    public boolean saveOrUpdateImage(Integer uid, String bucket, String object) {
        Date now = new Date();
        UserImage record = lambdaQuery().eq(UserImage::getUid, uid).one();
        int count = 0;
        if (record == null || record.getId() == null) {
            UserImage userImage = new UserImage();
            userImage.setUid(uid);
            userImage.setBucket(bucket);
            userImage.setObject(object);
            userImage.setCreateTime(now);
            userImage.setUpdateTime(now);
            count += userImageMapper.insert(userImage);
        } else {
            record.setBucket(bucket);
            record.setObject(object);
            record.setUpdateTime(now);
            count += userImageMapper.updateById(record);
        }
        return count > 0;
    }
}




