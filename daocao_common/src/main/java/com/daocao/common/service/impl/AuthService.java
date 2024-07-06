package com.daocao.common.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.domain.dto.LoginDto;
import com.daocao.common.domain.vo.LoginUserVO;
import com.daocao.common.exception.ServiceException;
import com.daocao.common.service.IAuthService;
import com.daocao.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    /**
     *login方法
     */

    @Override
    public String login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDto.getAccount(), loginDto.getPassword());
        // 调用了loaduserbyusername
        Authentication authenticate = authenticationManager.authenticate(authentication);
        // 获取用户信息，返回userDetails
        LoginUserVO loginUser = (LoginUserVO) authenticate.getPrincipal();
        // 根据loginUser创建token
        if (ObjectUtil.isNull(loginUser)){
            throw new ServiceException(HttpStatus.UNAUTHORIZED,"认证失败!");
        }
        // 创建token,此处的token是由UUID编码而成JWT字符串
        String token = jwtUtils.createToken(loginUser);
        log.info("token===>{}",token);
        return token;
    }
}
