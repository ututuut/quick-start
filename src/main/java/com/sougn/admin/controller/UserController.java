package com.sougn.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sougn.admin.controller.utils.Check;
import com.sougn.admin.controller.utils.Return;
import com.sougn.admin.controller.utils.viewlog.ViewLog;
import com.sougn.admin.entity.User;
import com.sougn.admin.service.UserService;

@Controller
@RequestMapping(value = "/user/v1")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private Check check;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ViewLog(description = "增加用户")
	@ResponseBody
	public Object addUser(String loginId, String passwd, @RequestParam(defaultValue = "", required = false) String name,
			@RequestParam(defaultValue = "", required = false) String title,
			@RequestParam(defaultValue = "", required = false) String imgPath,
			@RequestParam(defaultValue = "", required = false) String email,
			@RequestParam(defaultValue = "", required = false) String phone) throws Exception {
		
		if(check.isEmail(loginId) == false && check.isPhone(loginId) == false)
			return getReturn().setCode(20).setInfo("注册账号不是手机号或者邮箱");
		if(check.isPasswd(passwd) == false)
			return getReturn().setCode(20).setInfo("密码必须为数字和字母");
		
		User userHave = userService.findByLoginId(loginId);
		User user = new User();
		if (userHave == null) {
			user.setCreateTime(new Date());
			user.setEmail(email);
			user.setImgPath(imgPath);
			user.setLoginId(loginId);
			user.setName(name);
			user.setPasswd(passwd);
			user.setPhone(phone);
			user.setTitle(title);
			user.setUpdateTime(new Date());

			userService.save(user);
			return getReturn().setData(user);
		} else {
			return getReturn().setCode(20).setInfo("该账号已经存在");
		}

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ViewLog(description = "修改用户信息")
	@ResponseBody
	public Object updateUser(Integer id, String passwd, @RequestParam(defaultValue = "", required = false) String name,
			@RequestParam(defaultValue = "", required = false) String title,
			@RequestParam(defaultValue = "", required = false) String imgPath,
			@RequestParam(defaultValue = "", required = false) String email,
			@RequestParam(defaultValue = "", required = false) String phone) {

		User user = userService.getOne(id);

		user.setEmail(email);
		user.setImgPath(imgPath);
		user.setName(name);
		user.setPasswd(passwd);
		user.setPhone(phone);
		user.setTitle(title);
		user.setUpdateTime(new Date());

		userService.save(user);

		return getReturn().setData(user);
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ViewLog(description = "删除用户")
	@ResponseBody
	public Object delUser(Integer id) {

		User user = userService.getOne(id);

		userService.deleteById(id);

		return "";
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "用户翻页")
	public Object pageUser(User user, Integer page, Integer size, String sort, String order) {
		return userService.findAllList(user, page, size, sort, order);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ViewLog(description = "用户登录")
	@ResponseBody
	public Object login(String loginId, String passwd, HttpSession httpSession) throws Exception {
		User user = userService.findByLoginIdAndPasswd(loginId, passwd);
		if (user == null) {
			return getReturn().setCode(20).setInfo("改账号不存在，或者密码错误");
		} else {
			httpSession.setAttribute("user", user);
			return user;
		}
	}

	@RequestMapping(value = "/getUser",method  =RequestMethod.POST)
	@ResponseBody
	@ViewLog(description = "获取登录用户的信息")
	public Object getUser( HttpSession httpSession) {
		User user = (User)httpSession.getAttribute("user");
		if(user == null)
		{
			return getReturn().setCode(20).setInfo("您还没有登录，或者已经超时退出登录了");
		}else {
			return getReturn().setData(user);
		}
	}
	
	public Return getReturn() {
		return new Return();
	}

}
