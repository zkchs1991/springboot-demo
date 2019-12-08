package com.zk.shiro.common.aspect;

import com.zk.shiro.common.exception.RRException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Redis切面处理类
 */
@Aspect
@Component
public class RedisAspect {

    private static final Logger log = LogManager.getLogger();

    //是否开启redis缓存  true开启   false关闭
    @Value("${shiro.redis.open: false}")
    private boolean open;

    @Around("execution(* com.zk.shiro.common.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if (open) {
            try {
                result = point.proceed();
            } catch (Exception e) {
                log.error("redis error", e);
                throw new RRException("Redis服务异常");
            }
        }
        return result;
    }
}
