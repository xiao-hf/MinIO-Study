package com.xiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.entity.UserInfo;
import com.xiao.service.UserInfoService;
import com.xiao.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    @Override
    public UserInfo getUserById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectUserById(id);
        return userInfo;
    }
}




