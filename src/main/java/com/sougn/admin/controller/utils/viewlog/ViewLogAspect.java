package com.sougn.admin.controller.utils.viewlog;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.sougn.admin.controller.utils.IP;
import com.sougn.admin.entity.User;
import com.sougn.admin.entity.ViewLog;
import com.sougn.admin.service.ViewLogService;

@Aspect
@Component
public class ViewLogAspect {

	private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
	
	@Autowired
	private ViewLogService viewLogService;
	
	@Autowired
	private IP ip;
	
	@Autowired(required = false)
    private HttpServletRequest request;
	
	@Pointcut("@annotation(com.sougn.admin.controller.utils.viewlog.ViewLog)")
    public void controllerAspect() {
    }
	
	@Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        //线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime=new Date();
        beginTimeThreadLocal.set(beginTime);
    }
	
	@After("controllerAspect()")
    public void after(JoinPoint joinPoint) throws Exception{
		ViewLog viewLog = new ViewLog();
		viewLog.setCreateTime(new Date());
		viewLog.setIp(ip.getIpAddr(request));
		viewLog.setLogName(getControllerMethodDescription(joinPoint));
		//请求参数
        Map<String,String[]> logParams=request.getParameterMap();
		viewLog.setParams(JSON.toJSONString(logParams));
		Date logStartTime = beginTimeThreadLocal.get();

		User user = (User)request.getSession().getAttribute("user");
		
        long beginTime = beginTimeThreadLocal.get().getTime();
        long endTime = System.currentTimeMillis();
        //请求耗时
        Long logElapsedTime = endTime - beginTime;
		viewLog.setTtl(logElapsedTime.intValue());
		viewLog.setType(request.getMethod());
		viewLog.setLogName(getControllerMethodDescription(joinPoint));
		viewLog.setUrl(request.getRequestURI());
		if(user != null) {
			viewLog.setUser(user);
		}
		
		
		viewLogService.save(viewLog);
	}
	
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception{

        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取相关参数
        Object[] arguments = joinPoint.getArgs();
        //生成类对象
        Class targetClass = Class.forName(targetName);
        //获取该类中的方法
        Method[] methods = targetClass.getMethods();

        String description = "";

        for(Method method : methods) {
            if(!method.getName().equals(methodName)) {
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if(clazzs.length != arguments.length) {
                //比较方法中参数个数与从切点中获取的参数个数是否相同，原因是方法可以重载哦
                continue;
            }
            description = method.getAnnotation(com.sougn.admin.controller.utils.viewlog.ViewLog.class).description();
        }
        return description;
    }
	
}
