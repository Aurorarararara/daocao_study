package com.daocao.sysuser.controller;


import com.daocao.common.response.DaoCaoResult;
import com.daocao.sysuser.service.ISysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/user")
public class UserController {

    private final ISysUserService iSysUserService;

    public UserController(ISysUserService iSysUserService) {
        this.iSysUserService = iSysUserService;
    }


    @GetMapping("/info")
    public DaoCaoResult searchUserInfo(){
        return DaoCaoResult.success(iSysUserService.searchUserInfo());
    }

}
