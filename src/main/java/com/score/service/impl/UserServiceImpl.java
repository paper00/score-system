package com.score.service.impl;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.score.bean.User;
import com.score.mapper.UserMapper;
import com.score.service.UserService;
import com.score.utils.JwtLoginFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/13
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	@Override
	public User getObj(String username) {
		return baseMapper.selectById(username);
	}

	@Override
	public String getSecretkey(String username) {
		return baseMapper.getSecretkey(username);
	}
	
	@Override
	public int logout() {
		return setEnabled(false);
	}

	@Override
	public int setEnabled(boolean enabled) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = getObj(username);
		user.setActive(enabled);
		return baseMapper.updateById(user);
	}

	@Override
	public int active(String username) {
		User user = getObj(username);
		user.setActive(true);
		return baseMapper.updateById(user);
	}

}
