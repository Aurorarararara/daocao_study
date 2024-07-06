package com.daocao.support.config.security;


import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.domain.entity.UmsMenu;
import com.daocao.common.domain.entity.UmsRole;
import com.daocao.common.domain.entity.UmsSysUser;
import com.daocao.common.domain.vo.LoginUserVO;
import com.daocao.common.mapper.UmsMenuMapper;
import com.daocao.common.mapper.UmsSysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysUserDetailsService implements UserDetailsService {

    private final UmsSysUserMapper sysUserMapper;
    private final UmsMenuMapper umsMenuMapper;

    public SysUserDetailsService(UmsSysUserMapper sysUserMapper, UmsMenuMapper umsMenuMapper) {
        this.sysUserMapper = sysUserMapper;
        this.umsMenuMapper = umsMenuMapper;
    }

    /**
     * 实现方法，在此方法中根据用户名查询用户
     * 账号：用户名、手机号、邮箱，通过正则表达式判断账号类型
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        log.info("loadUserByUsername=======>{}", account);
        // TODO 验证账号类型
        int accountType = 0;
        // 根据账号查询用户，同时将角色查出来，联查时最好不要超过3张表
        UmsSysUser sysUser = sysUserMapper.selectUserByUserName(account, accountType);
        log.info("sysUser=========>{}", sysUser);
        // 权限查询是根据角色查询的，首先获取所有的角色id
        if (ObjectUtil.isNotNull(sysUser)) {
            List<UmsRole> roleList = sysUser.getRoleList();
            // 取出id
            List<Long> roleIds = roleList.stream().map(UmsRole::getRoleId).collect(Collectors.toList());
            log.info("角色id====》{}", roleIds);
            // 查询所有的菜单
            List<UmsMenu> menuList = umsMenuMapper.selectByRoleIds(roleIds);
            // 获取list中的权限字段
            List<String> perms = menuList.stream().map(UmsMenu::getPerms).collect(Collectors.toList());
            log.info("权限====》{}", perms);
            sysUser.setPerms(perms);
        }

        // 根据角色查询权限 menu
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setSysUser(sysUser);
        loginUserVO.setId(sysUser.getId());
        return loginUserVO;
    }
}