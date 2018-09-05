package com.sougn.admin.controller.utils.viewlog;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewLog {

	//日志描述
	String description() default "";
	//不记录的日志字段
	String notLogParams() default "";
}
