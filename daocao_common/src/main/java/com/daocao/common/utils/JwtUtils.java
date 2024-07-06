package com.daocao.common.utils;

import cn.hutool.core.util.StrUtil;
import com.daocao.common.constants.CacheConstants;
import com.daocao.common.domain.vo.LoginUserVO;
import com.daocao.common.utils.redis.RedisCacheUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过JWT去生成token和解析token，刷新token
 */
@Component
public class JwtUtils {

    private String secret = "qwertyuioplkjhgfdsazxcvbnm";
    @Autowired
    private RedisCacheUtil redisCacheUtil;


    public JwtUtils(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }


    /**
     * 创建token，会将用户数据存放在redis中
     * 可以方便的实现单点登录，实现踢人下线，查看在线用户等等功能
     * 可以使用UUID当作redis的key
     */
    public String createToken(LoginUserVO loginUserVO) {
        // 创建一个map
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        // 将UUID存储到登录用户中，可以在后台系统中根据token值获取redis中的数据
        loginUserVO.setToken(token);
        // 设置登录时间
        loginUserVO.setLoginTime(System.currentTimeMillis());
        Map<String, Object> claims = new HashMap<>();
        claims.put("token", token);
        // 将用户数据缓存到redis中
        // 调用刷新token的方法
        refreshToken(loginUserVO);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析token
     * token:JWT字符串****.****.****
     */

    public Claims parseToken(String token) {
        // 解析token
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取登录用户
     * 根据token，解析之后从redis中获取
     * 并且刷新token
     */
    public Object getLoginUser(HttpServletRequest request) {
        // 通过Jwt加密过的
        String token = request.getHeader("Daocao-Authorization");
        if (StrUtil.isNotEmpty(token)){
            Claims claims = parseToken(token);
            String parseToken = (String) claims.get("token");
            // 从redis获取数据
            LoginUserVO loginUserVO = redisCacheUtil.getCacheObject(CacheConstants.LOGIN_USER_KEY + parseToken);
            // 刷新token
            // 获取登录时间
            long loginTime = loginUserVO.getLoginTime();
            // 获取当前时间
            long currentTimeMillis = System.currentTimeMillis();
            // 判断是否相差20分钟
            long mills = currentTimeMillis / 1000 / 60 - loginTime/1000/60;
            if (mills >= 20) {
                refreshToken(loginUserVO);
            }
            return loginUserVO;
        }
        return null;
    }

    private void refreshToken(LoginUserVO loginUserVO) {
        // 将用户数据缓存到redis中
        redisCacheUtil.setCacheObject(CacheConstants.LOGIN_USER_KEY + loginUserVO.getToken(), loginUserVO, 30, TimeUnit.MINUTES);
    }
}
