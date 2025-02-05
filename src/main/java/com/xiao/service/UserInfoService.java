package com.xiao.service;

import com.xiao.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_user_info】的数据库操作Service
* @createDate 2025-02-03 18:26:00
*/
public interface UserInfoService extends IService<UserInfo> {

    UserInfo getUserById(Integer id);

    boolean delUser(Integer id);

    List<UserInfo> getUserList();
}
