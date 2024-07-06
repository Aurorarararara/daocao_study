package com.daocao.auth.controller;


import com.daocao.common.domain.entity.UmsSysUser;
import com.daocao.common.service.IUmsSysUserService;
import com.daocao.common.response.DaoCaoResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ums/sysuser")
public class UmsSysUserController {

    private final IUmsSysUserService sysUserService;

    public UmsSysUserController(IUmsSysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /*
    新增用户接口
     */
    @PostMapping
    public DaoCaoResult addSysUser(@RequestBody UmsSysUser user){
        boolean flag = sysUserService.save(user);
        if(flag){
            return DaoCaoResult.success();
        }
        return DaoCaoResult.error();
    }

    /*
    查询用户列表接口
     */
    @GetMapping
    public DaoCaoResult searchList(){
        List<UmsSysUser> list = sysUserService.list();
        list.forEach(System.out::println);
        return DaoCaoResult.success().put("data",list);
    }
}
