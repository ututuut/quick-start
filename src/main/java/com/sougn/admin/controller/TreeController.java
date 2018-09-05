package com.sougn.admin.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sougn.admin.controller.utils.Return;
import com.sougn.admin.controller.utils.viewlog.ViewLog;
import com.sougn.admin.entity.Tree;
import com.sougn.admin.service.TreeService;

@Controller
@RequestMapping(value = "/tree/v1")
public class TreeController {

	@Autowired
	private TreeService treeService;

	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "增加目录树节点")
	public Object add(String type, String path, String name,
			@RequestParam(defaultValue = "", required = false) String icon,
			@RequestParam(defaultValue = "", required = false) Integer sort,
			@RequestParam(defaultValue = "", required = false) Integer parentId) {
		Tree tree = new Tree();
		tree.setIcon(icon);
		tree.setName(name);
		tree.setPath(path);
		tree.setSort(sort);
		tree.setType(type);
		tree.setUpdateDate(new Date());
		tree.setCreateDate(new Date());
		if (parentId == null || treeService.getOne(parentId) == null) {
			tree.setParentId(null);
		} else {
			tree.setParentId(parentId);
		}

		treeService.save(tree);

		return getReturn().setData(tree);
	}

	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ViewLog(description = "删除目录树节点")
	@ResponseBody
	public Object del(String id) {
		String[] ids = id.split(",");
		for (String idEl : ids) {
			treeService.deleteById(new Integer(idEl));
		}
		return "";
	}

	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "更新目录树节点")
	public Object update(String type, String path, String name, Integer id,
			@RequestParam(defaultValue = "", required = false) String icon,
			@RequestParam(defaultValue = "", required = false) Integer sort,
			@RequestParam(defaultValue = "", required = false) Integer parentId) {
		Tree tree = treeService.getOne(id);
		tree.setIcon(icon);
		tree.setName(name);
		tree.setPath(path);
		tree.setSort(sort);
		tree.setType(type);
		tree.setUpdateDate(new Date());
		treeService.save(tree);

		return "";
	}

	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "获取权限下的树节点")
	public Object list(Integer id) {
		return treeService.getTree(id);
	}
	
	@Lookup
	public Return getReturn() {
		return new Return();
	}

}
