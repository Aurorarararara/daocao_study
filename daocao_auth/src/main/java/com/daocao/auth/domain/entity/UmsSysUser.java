package com.daocao.auth.domain.entity;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("ums_sys_user")
public class UmsSysUser implements Serializable {
    private long id;
    private String username;
    private String nickname;
    private String email;
    private String mobile;
    //用Integer
    private Integer sex;
    private String avatar;
    private String password;
    private Integer status;
    private String creator;
    private String updater;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String remark;
    //逻辑删除 Mybatis-plus 默认0是未删除，1是已删除
    @TableLogic(value = "1",delval = "0")
    private Integer deleted;
}

