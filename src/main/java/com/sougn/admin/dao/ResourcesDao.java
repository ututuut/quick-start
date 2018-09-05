package com.sougn.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sougn.admin.entity.Resources;

public interface ResourcesDao  extends JpaRepository<Resources, Integer> {
	
	public Resources findByTypeAndResName(String type,String resName);
	
}