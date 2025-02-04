package com.xiao.mapper;

import com.xiao.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【t_user_info】的数据库操作Mapper
* @createDate 2025-02-03 18:26:00
* @Entity com.xiao.entity.UserInfo
*/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo selectUserById(Integer id);
}




