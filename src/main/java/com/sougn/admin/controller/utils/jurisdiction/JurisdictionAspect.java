package com.sougn.admin.controller.utils.jurisdiction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sougn.admin.entity.User;
import com.sougn.admin.service.ResourcesService;

//@Aspect
//@Component
public class JurisdictionAspect {

	@Autowired(required = false)
	private HttpSession session;
	
	@Autowired
	private ResourcesService resourcesService; 
	
	@Autowired(required = false)
	private HttpServletRequest request;
	
	@Pointcut("@annotation(com.sougn.admin.controller.utils.jurisdiction.Jurisdiction)")
	public void controllerAspect() {
		
	}
	
	@Before("controllerAspect")
	public void doDefore(JoinPoint joinPoint)throws Exception{
		
		User user = (User) session.getAttribute("user");
		String url = request.getRequestURI();
		
		
		
	}
	
	
	
}
