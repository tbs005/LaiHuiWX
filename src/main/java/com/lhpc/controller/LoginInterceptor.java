package com.lhpc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lhpc.model.User;

/**
 * 拦截器
 * @author YangGuang
 */
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private HttpSession session;
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		// System.out.println("afterCompletion---");
	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
		// System.out.println("postHandle---");
	}

	// TODO 拦截
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		User user = (User) session.getAttribute("CURRENT_USER");
		if (user == null) {
			response.setContentType("application/json");
			response.sendRedirect("/user/noLogin");
			return false;
		} else {
			return true;
		}
	}
}
