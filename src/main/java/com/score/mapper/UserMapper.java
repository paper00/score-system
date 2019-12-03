package com.score.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.score.bean.User;

import io.lettuce.core.dynamic.annotation.Param;

/**
 * @author paper
 * @date 2019/11/06
 */
public interface UserMapper extends BaseMapper<User>{

	/**
	 * @param username
	 * @return
	 */
	String getSecretkey(@Param("username") String username);
	
}
