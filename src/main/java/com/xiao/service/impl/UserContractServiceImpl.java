package com.xiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.entity.UserContract;
import com.xiao.service.UserContractService;
import com.xiao.mapper.UserContractMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【t_user_contract】的数据库操作Service实现
* @createDate 2025-02-03 18:24:35
*/
@Service
public class UserContractServiceImpl extends ServiceImpl<UserContractMapper, UserContract>
    implements UserContractService{

}




