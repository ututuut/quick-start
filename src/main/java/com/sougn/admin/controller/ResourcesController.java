package com.sougn.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sougn.admin.controller.utils.Return;
import com.sougn.admin.controller.utils.viewlog.ViewLog;
import com.sougn.admin.entity.Resources;
import com.sougn.admin.entity.Role;
import com.sougn.admin.service.ResourcesService;
import com.sougn.admin.service.RoleService;

@Controller
@RequestMapping(value = "/resources/v1")
public class ResourcesController {

	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
    private WebApplicationContext applicationContext;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "添加新的资源")
	public Object add(String resName, String type){
		Resources resources = new Resources();
		resources.setResName(resName);
		resources.setType(type);
		resources.setCreateTime(new Date());
		resources.setUpdateTime(new Date());
		
		resourcesService.save(resources);
		
		return getReturn().setData(resources);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "修改资源")
	public Object update(String resName, String type , Integer id) throws Exception{
		
		Resources resources = resourcesService.getOne(id);
		if(resources == null)
		{
			return getReturn().setCode(20).setInfo("资源不存在");
		}else {
			resources.setResName(resName);
			resources.setType(type);
			resources.setUpdateTime(new Date());
		}
		resourcesService.save(resources);
		
		return getReturn().setData(resources);
	}
	

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "添加新的资源")
	public Object delete(String id) throws Exception{
		
		String[] ids = id.split(",");
		
		for(String idEl : ids) {
			resourcesService.deleteById(new Integer(idEl));
		}
		
		return "";
	}
	
	@RequestMapping(value="/add/role",method=RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "为资源添加角色")
	public Object addRole(String role,String resourceId){
		
		String[] roles = role.split(",");
		Resources resources = resourcesService.getOne(new Integer(resourceId));
		
		for(String roleEl:roles) {
			Role roleObj = roleService.getOne(new Integer(roleEl));
			roleObj.getResources().add(resources);
			
			roleService.save(roleObj);
		}
		
		return getReturn().setData(resources);
	}
	

	@RequestMapping(value="/del/role",method=RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "移除该资源的角色")
	public Object delRole(String role,String resourceId){
		
		String[] roles = role.split(",");
		
		for(String roleEl:roles) {
			Role roleObj = roleService.getOne(new Integer(roleEl));
			List<Resources> resourcesList = roleObj.getResources();
			
			resourcesList.remove(roleObj);
			
			roleService.save(roleObj);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/role/resources")
	@ResponseBody
	@ViewLog(description = "获取角色下的资源")
	public Object roleResources(String roleId) {
		List<Resources> resList = roleService.getOne(new Integer(roleId)).getResources();
		return getReturn().setData(resList);
	}
	
//	@RequestMapping(value = "/get/allUrl",method = RequestMethod.POST)
//	@ResponseBody
//	@ViewLog(description = "获取所有URL")
//	public Object getAllUrl() {
//		RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
//        //获取url与类和方法的对应信息
//        Map<RequestMappingInfo,HandlerMethod> map = mapping.getHandlerMethods();
//        List<String> urlList = new ArrayList<>();
//        for (RequestMappingInfo info : map.keySet()){
//            //获取url的Set集合，一个方法可能对应多个url
//            Set<String> patterns = info.getPatternsCondition().getPatterns();
//            for (String url : patterns){
//                urlList.add(url);
//            }
//        }
//        return getReturn().setData(urlList);
//	}

	@Lookup
	public Return getReturn() {
		return new Return();
	}
	
}
