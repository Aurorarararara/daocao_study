package com.daocao.common.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@TableName("ums_menu")
public class UmsMenu implements Serializable {
    @TableId
    private long id;
    private long parentId;
    private String menuName;
    private String path;
    private String componentPath;
    private String perms;
    private Integer icon;
    private Integer menuType;
    private Integer sort;
    private Integer status;

    private String creator;
    private String updater;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private String remark;
    @TableLogic
    private Integer deleted;


    @TableField(exist = false)
    private List<UmsMenu> children = new ArrayList<>();


















}
