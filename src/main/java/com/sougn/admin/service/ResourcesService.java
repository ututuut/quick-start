package com.sougn.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sougn.admin.dao.ResourcesDao;
import com.sougn.admin.entity.Resources;

@Service
public class ResourcesService {

	@Autowired
	public ResourcesDao resourcesDao;
	
	public void save(Resources resources) {
		resourcesDao.save(resources);
	}
	
	public void delete(Resources resources) {
		resourcesDao.delete(resources);
	}
	
	public Resources getOne(Integer id) {
		return resourcesDao.getOne(id);
	}
	
	public void deleteById(Integer id) {
		resourcesDao.deleteById(id);
	}
	
	public Resources findByTypeAndResName(String type , String resName) {
		return resourcesDao.findByTypeAndResName(type, resName);
	}
	
}
