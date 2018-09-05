package com.sougn.admin.controller.utils.jurisdiction;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sougn.admin.entity.Resources;
import com.sougn.admin.entity.Role;
import com.sougn.admin.entity.User;
import com.sougn.admin.service.ResourcesService;
import com.sougn.admin.service.RoleService;

@Component
public class UrlFilter implements Filter {

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private RoleService roleService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String url = httpRequest.getRequestURI();

		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null)// 用户没有登录
		{
			Resources res = resourcesService.findByTypeAndResName("URL", url);
			if (res == null) {
				chain.doFilter(request, response);
				return;
			}
		} else {// 用户登录
			List<Role> roleList = user.getRole();

			for (Role role : roleList) {
				List<Resources> resourcesList = role.getResources();
				for (Resources resources : resourcesList) {
					if (resources.getType().equals("URL") && url.equals(resources.getResName())) {// 禁止访问的资源
						httpResponse.sendError(403);
						return;
					}
				}
			}
		}

		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
