package com.sougn.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sougn.admin.dao.ResourcesDao;
import com.sougn.admin.dao.RoleDao;
import com.sougn.admin.entity.Role;

@Service
public class RoleService {

	@Autowired
	public RoleDao roleDao;
	
	public void save(Role role) {
		roleDao.save(role);
	}
	
	public void delete(Integer id) {
		roleDao.deleteById(id);
	}
	
	public void update(Role role) {
		roleDao.save(role);
	}
	
	public Role getOne(Integer id) {
		return roleDao.getOne(id);
	}
	
	public List<Role> getList(){
		return roleDao.findAll();
	}
}
