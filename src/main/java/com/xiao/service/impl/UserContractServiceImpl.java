package com.xiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiao.entity.UserContract;
import com.xiao.service.UserContractService;
import com.xiao.mapper.UserContractMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author Administrator
* @description 针对表【t_user_contract】的数据库操作Service实现
* @createDate 2025-02-03 18:24:35
*/
@Service
public class UserContractServiceImpl extends ServiceImpl<UserContractMapper, UserContract>
    implements UserContractService{

    @Resource
    UserContractMapper userContractMapper;

    @Override
    public boolean saveOrUpdateImage(Integer uid, String bucket, String object) {
        Date now = new Date();
        int count = 0;
        UserContract record = lambdaQuery().eq(UserContract::getUid, uid).one();
        if (record == null || record.getId() == null) {
            UserContract userContract = new UserContract();
            userContract.setUid(uid);
            userContract.setBucket(bucket);
            userContract.setObject(object);
            userContract.setUpdateTime(now);
            userContract.setCreateTime(now);
            count += userContractMapper.insert(userContract);
        } else {
            record.setBucket(bucket);
            record.setObject(object);
            record.setUpdateTime(now);
            count += userContractMapper.updateById(record);
        }
        return count > 0;
    }
}




