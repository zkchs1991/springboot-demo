package com.zk.shiro.common.exception;

import com.google.gson.Gson;
import com.zk.shiro.common.utils.R;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 异常处理器
 *
 */
@Component
public class RRExceptionHandler implements HandlerExceptionResolver {

    private static final Gson gson = new Gson();
    private static final Logger logger = LogManager.getLogger();

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
		R r;
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			
			if (ex instanceof RRException) {
                RRException e=((RRException) ex);
                r = R.error(e.getCode(), e.getMsg());
                r.put("errorid", MDC.get("requestId"));
                StackTraceElement[] stackTrace = e.getStackTrace();
                if(e.getArgs() == null || e.getArgs().length == 0) {
                    logger.error(e.getMsg());
                } else {
                    if(stackTrace != null && stackTrace.length > 1) {
                        logger.warn(stackTrace[1].toString());
                    }
                }
			} else{
                r = R.error();
                r.put("errorid", MDC.get("requestId"));
                if(ex.getMessage()==null){
                    logger.error(ex.getMessage(), ex);
                }
                if(ex.getMessage()!=null&&!ex.getMessage().contains("Subject does not have permission")){
                    //记录异常日志
                    //logger.error(ex.getMessage(), ex);
                    logger.error(ex.toString());
                    for (StackTraceElement s : ex.getStackTrace()) {
                        logger.error(s.toString());
                    }
                }
            }
            Map<String,Object> p=new HashMap<>();
            p.put("code",r.get("code"));
            p.put("msg",r.get("msg"));
            p.put("errorid",r.get("errorid"));
			response.getWriter().print(gson.toJson(p));
		} catch (Exception e) {
			logger.error("RRExceptionHandler 异常处理失败", e);
		}
		return new ModelAndView();
	}
}
