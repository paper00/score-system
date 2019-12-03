package com.score.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.OutputStream;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.score.bean.User;
import com.score.exception.UserException;
import com.score.service.UserService;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/04
 * @description 验证token的过滤器
 */
@Slf4j
public class JwtFilter extends GenericFilterBean {
	
	@Resource
	UserService userService;
	ServletContext servletContext = null;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().toString();
		
		if ((path.length() >= 8 && path.substring(0, 8).equals("/student"))
			|| (path.length() >= 8 && path.substring(0, 8).equals("/teacher"))
			|| (path.length() >= 6 && path.substring(0, 6).equals("/admin"))) {
			// 获取请求头里的token
			String jwtToken = req.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
			if (jwtToken != null) {
				jwtToken = jwtToken.substring(7); 
				Claims claims = null;
				try {
					claims = JwtTokenUtil.parseJWT(jwtToken);
				} catch (UserException e) {
					e.printStackTrace();
				}
				// 判断持有token的用户是否登录
				// 此时UserService还没被注入，要通过WebApplicationContext手动注入
				String username = claims.getSubject();
				servletContext = req.getSession().getServletContext();
				WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				userService = (UserService) cxt.getBean(UserService.class);
				User user = userService.getObj(username);
				// 若已登录继续访问，否则返回未登录的响应信息
				if (user.isActive()) {
					List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String)claims.get("authorities"));
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(token);
					String authorized = (String)claims.get("authorities");
					log.info("[" + authorized.substring(0, authorized.length()-1) + "] " + username + " 发起请求");
					chain.doFilter(req, response);
				} else {
					response.reset();
					response.setContentType("application/json;charset=utf-8");
					PrintWriter printWriter = response.getWriter();
					printWriter.write(JSON.toJSONString(Result.UserError().appendInfo("未登录")));
					printWriter.flush();
					printWriter.close();
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}
}
