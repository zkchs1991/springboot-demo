package com.zk.shiro.common.interceptor;


import com.zk.mp.entity.sys.SysUserEntity;
import com.zk.shiro.common.exception.RRException;
import com.zk.shiro.common.utils.RedisUtils;
import com.zk.shiro.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * 同一时间单用户登录 验证
 */
@Component
public class SingleLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (ShiroUtils.isLogin()){
            SysUserEntity sysUser = ShiroUtils.getUserEntity();
            String cachedSessionId = redisUtils.get(RedisUtils.shiro_session_key + sysUser.getUsername());

            if (!Objects.equals(cachedSessionId, ShiroUtils.getSession().getId())){
                System.out.println("有他人登录，强制登出");
                ShiroUtils.logout();
                throw new RRException("有他人登录，强制登出");
            }
        }
        return true;
    }
}
