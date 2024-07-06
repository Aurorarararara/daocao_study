package com.daocao.sysuser.controller;

import com.daocao.common.domain.entity.UmsMenu;
import com.daocao.common.domain.vo.RouterVO;
import com.daocao.common.response.DaoCaoResult;
import com.daocao.common.service.IUmsMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sys/menu")
public class MenuController {

    private final IUmsMenuService menuService;

    public MenuController(IUmsMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     *查询自己的菜单
     */
    @GetMapping("self")
    public DaoCaoResult searchSelfMenu(){
        // 获取当前登录的用户id，都在SecurityContextHolder中存储
        List<RouterVO> routerVOList = menuService.searchSelfMenu();
        return DaoCaoResult.success(routerVOList);

    }

    @GetMapping("list")
    public DaoCaoResult list(){
        // 获取当前登录的用户id，都在SecurityContextHolder中存储
        List<UmsMenu> list = menuService.selectMenuList();
        return DaoCaoResult.success(list);

    }
}
