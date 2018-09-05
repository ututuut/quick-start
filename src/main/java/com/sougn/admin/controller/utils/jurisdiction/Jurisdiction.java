package com.sougn.admin.controller.utils.jurisdiction;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Jurisdiction {

	String type() default "";//资源类型
	
	String resources() default "";//资源
	
}
