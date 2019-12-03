package com.score.service.impl;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.score.bean.Role;
import com.score.bean.User;
import com.score.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/13
 */
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Resource
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getObj(username);
		if (user != null) {
			user.setRoles(Arrays.asList(new Role(user.getRole())));
		} else {
			log.info("用户名不存在");
		}
		return user;
	}

}
