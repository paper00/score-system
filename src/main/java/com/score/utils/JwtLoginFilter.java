package com.score.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.score.bean.Teacher;
import com.score.bean.User;
import com.score.exception.UserException;
import com.score.service.TeacherService;
import com.score.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/03
 * @description 用户登录过滤器
 */
@Slf4j
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

	@Resource
	UserService userService;
	ServletContext servletContext = null;
	
	public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		servletContext = request.getSession().getServletContext();
		User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		return getAuthenticationManager().authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		// 此时UserService还没被注入，要通过WebApplicationContext手动注入
		WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		userService = (UserService) cxt.getBean(UserService.class);
		// 激活用户
		userService.active(authResult.getName());
		// 创建token
		Collection<? extends GrantedAuthority> authorities  = authResult.getAuthorities();
		String roles = "";
		for (GrantedAuthority authority : authorities) {
			roles = roles + authority.getAuthority() + ",";
		}
		String token = null;
		try {
			token = JwtTokenUtil.createJWT(authResult.getName(), roles, null);
		} catch (UserException e) {
			e.printStackTrace();
		}
		String raw_role = roles.substring(0, roles.length()-1);
        // 将token放在响应头返回，body里返回用户权限
		response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, token);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(JSON.toJSONString(Result.OK().appendInfo(raw_role.substring(5).toLowerCase())));
		printWriter.flush();
		printWriter.close();
        log.info("[" + raw_role + "] " + authResult.getName() + " 登录");
		log.info("[Token] " + response.getHeader(JwtTokenUtil.AUTH_HEADER_KEY));
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(JSON.toJSONString(Result.UserError().appendInfo("用户名或密码错误")));
		printWriter.flush();
		printWriter.close();
	}
	
}
