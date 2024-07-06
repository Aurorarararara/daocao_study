package com.daocao.common.domain.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RouterVO {
    private Long id;
    private Long parentId;
    private String menuName;
    private String path;
    private String componentName;
    private String icon;
    private Integer menuType;
    private List<RouterVO> children = new ArrayList<>();
}
