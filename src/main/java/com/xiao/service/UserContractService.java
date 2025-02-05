package com.xiao.service;

import com.xiao.entity.UserContract;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【t_user_contract】的数据库操作Service
* @createDate 2025-02-03 18:24:35
*/
public interface UserContractService extends IService<UserContract> {
    boolean saveOrUpdateImage(Integer uid, String bucket, String object);
}
