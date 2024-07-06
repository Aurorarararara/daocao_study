package com.daocao.auth.controller;


import com.daocao.common.domain.dto.LoginDto;
import com.daocao.common.service.IAuthService;
import com.daocao.common.response.DaoCaoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/*
认证接口
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    private final IAuthService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(IAuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 系统用户登录
     */
    @PostMapping("sys")
    public DaoCaoResult sysLogin(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        return DaoCaoResult.success().put("token", token);
    }


}
