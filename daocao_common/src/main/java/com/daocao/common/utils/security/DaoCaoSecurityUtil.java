package com.daocao.common.utils.security;

import cn.hutool.core.util.ObjectUtil;
import com.daocao.common.domain.vo.LoginUserVO;
import com.daocao.common.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class DaoCaoSecurityUtil {
    /**
     * 获取Authentication
     * @return
     */
    private static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 获取用户
     */
    public static LoginUserVO getLoginUser(){
        return (LoginUserVO)getAuthentication().getPrincipal();
    }


    /**
     * 获取用户id
     * @return
     */
    public static Long getUserid(){
        long userId = getLoginUser().getId();
        if (ObjectUtil.isNull(userId)){
            throw new ServiceException(HttpStatus.FORBIDDEN,"");
        }
        return userId;
    }


    /**
     * 获取用户名
     * @return
     */
    public static Long getUsername(){
        long userId = getLoginUser().getId();
        return userId;
    }
}
