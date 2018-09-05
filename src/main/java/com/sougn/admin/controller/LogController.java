package com.sougn.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sougn.admin.controller.utils.Return;
import com.sougn.admin.controller.utils.viewlog.ViewLog;
import com.sougn.admin.entity.search.SearchViewLog;
import com.sougn.admin.service.ViewLogService;

@Controller
@RequestMapping(value = "/log/v1")
public class LogController {

	@Autowired
	private ViewLogService viewLogService;
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "查询所有的日志")
	public Object pageUser(SearchViewLog searchViewLog, Integer page, Integer size, String sort, String order) {
		return viewLogService.findAllList(searchViewLog, page, size, sort, order);
	}
	
	@RequestMapping(value="/del",method = RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "删除指定日志")
	public Object delete(Integer id) {
		viewLogService.delete(id);
		return getReturn();
	}
	
	private Return getReturn() {
		return new Return();
	}
	
	
}
