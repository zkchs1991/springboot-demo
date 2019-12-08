package com.zk.mp.aspect;

import com.zk.datasources.DataSourceNames;
import com.zk.datasources.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 *
 * @date 2019/11/19 22:20
 */
@Aspect
@Component
public class DataSourceBaseMapperAspect implements Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.baomidou.mybatisplus.core.mapper.BaseMapper.*(..))  ")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        String methodName = method.getName();
        if(!methodName.startsWith("select")||methodName.equals("selectOne")||methodName.equals("selectById")){
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
//            logger.info("-----------------------------------"+methodName+"---------------set datasource is "+DataSourceNames.FIRST+"-------------");
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        }else {
            DynamicDataSource.setDataSource(DataSourceNames.SECOND);
//            logger.info("-----------------------------------"+methodName+"---------------set datasource is "+DataSourceNames.SECOND+"-------------");
            logger.debug("set datasource is " + DataSourceNames.SECOND);
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
