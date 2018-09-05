package com.sougn.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sougn.admin.controller.utils.Return;
import com.sougn.admin.controller.utils.viewlog.ViewLog;
import com.sougn.admin.entity.Role;
import com.sougn.admin.service.RoleService;

@Controller
@RequestMapping(value = "/role/v1")
public class RoleController {

	
	@Autowired
	private RoleService roleService;
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ViewLog(description="添加角色")
	@ResponseBody
	public Object add(String name) {
		Role role = new Role();
		role.setName(name);
		roleService.save(role);
		return getReturn().setData(role);
	}
	

	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ViewLog(description="修改角色")
	@ResponseBody
	public Object update(String name,Integer id) {
		Role role = roleService.getOne(id);
		role.setName(name);
		roleService.save(role);		
		return getReturn().setData(role);
	}
	
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ViewLog(description="删除角色")
	@ResponseBody
	public Object del(String role) {
		String[] roles = role.split(",");
		
		for(String id:roles) {
			roleService.delete(new Integer(id));
		}
		return null;
	}
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ViewLog(description="获取角色列表")
	@ResponseBody
	public Object getList() {
		return getReturn().setData(roleService.getList());
	}
	
	@Lookup
	public Return getReturn() {
		return new Return();
	}
}
