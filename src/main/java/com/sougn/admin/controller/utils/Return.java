package com.sougn.admin.controller.utils;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Return {
	
	private Integer code = 10;
	private String info ="正常";
	private Object data;
	
	public Return setCode(Integer code) {
		this.code = code;
		return this;
	}
	
	public Return setInfo(String info) {
		this.info = info;
		return this;
	}
	
	public Return setData(Object data) {
		this.data = data;
		return this;
	}
	
	
}
