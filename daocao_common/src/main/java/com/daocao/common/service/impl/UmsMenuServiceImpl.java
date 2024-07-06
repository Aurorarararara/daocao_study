package com.daocao.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daocao.common.domain.entity.UmsMenu;
import com.daocao.common.domain.vo.RouterVO;
import com.daocao.common.mapper.UmsMenuMapper;
import com.daocao.common.mapper.UmsRoleMapper;
import com.daocao.common.service.IUmsMenuService;
import com.daocao.common.utils.security.DaoCaoSecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {

    private final UmsRoleMapper roleMapper;

    public UmsMenuServiceImpl(UmsRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }


    /**
     * 获取个人菜单列表
     */
    @Override
    public List<RouterVO> searchSelfMenu() {
        Long userid = DaoCaoSecurityUtil.getUserid();
        List<Long> roleIds = roleMapper.selectByUserId(userid);
        List<UmsMenu> menuList = baseMapper.selectByRoleIds(roleIds);
        // 通过递归设置菜单的树形结构
        // 1.获取所有的1级菜单【parentId = 0】
        // 2.遍历1级菜单，获取他所有的子元素【其他数据的parentId = 当前元素的i】
        List<RouterVO> router = getRouter(menuList);
        router.forEach(System.out::println);
        return router;
    }


    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<UmsMenu> selectMenuList() {
        List<UmsMenu> menuList= baseMapper.selectList(null);
        return menuList;
    }

    /**
     * 获取路由
     */
    private List<RouterVO> getRouter(List<UmsMenu> menuList) {
        List<RouterVO> routerVOS = new ArrayList<>();
        // 首先获取所有的1级路由
        List<UmsMenu> parentMenu = menuList.stream().filter(item -> item.getParentId() == 0)
                .collect(Collectors.toList());
        // 转换对象
        for (UmsMenu menu : parentMenu) {
            RouterVO routerVO = new RouterVO();
            BeanUtil.copyProperties(menu, routerVO);
            routerVOS.add(routerVO);
        }
        //循环1级路由，获取所有的子菜单
        for (RouterVO routerVO : routerVOS) {
            //获取所有的子节点
            List<RouterVO> childrenList = buildTree(menuList, routerVO.getId());
            routerVO.setChildren(childrenList);

        }
        return routerVOS;
    }

    /**
     * 获取所有子节点，递归获取【如果是2级不需要递归了】
     */
    private List<RouterVO> buildTree(List<UmsMenu> allMenu, Long parentId) {
        List<RouterVO> childrenList = new ArrayList<>();
        // 便利所有的数据
        for (UmsMenu menu : allMenu) {
            // 判断menu的parentId是否与传进来的parentId相同
            if (Objects.equals(menu.getParentId(), parentId)) {
                RouterVO routerVO = new RouterVO();
                BeanUtil.copyProperties(menu, routerVO);
                childrenList.add(routerVO);
            }
        }
        //递归childrenList还有子节点
        for (RouterVO childrenItem : childrenList) {
            childrenItem.setChildren(buildTree(allMenu,childrenItem.getId()));
        }
        return childrenList;
    }
}
