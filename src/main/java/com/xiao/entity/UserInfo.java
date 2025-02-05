package com.xiao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.simpleframework.xml.Transient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @TableName t_user_info
 */
@TableName(value ="t_user_info")
@Data
public class UserInfo implements Serializable {

    @TableField(exist = false)
    private UserImage userImageDO;

    @TableField(exist = false)
    private UserContract userContractDO;

    /**
     * 主键id自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    @TableField(value = "nick")
    private String nick;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 性别（0女 1男）
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 手机
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 住址
     */
    @TableField(value = "address")
    private String address;


    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}