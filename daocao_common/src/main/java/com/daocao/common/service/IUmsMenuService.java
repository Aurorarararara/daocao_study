package com.daocao.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daocao.common.domain.entity.UmsMenu;
import com.daocao.common.domain.vo.RouterVO;

import java.util.List;


public interface IUmsMenuService extends IService<UmsMenu> {
    List<RouterVO> searchSelfMenu();

    List<UmsMenu> selectMenuList();
}
