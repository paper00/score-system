package com.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.score.bean.User;

/**
 * @author paper
 * @date 2019/11/12
 */
public interface UserService extends IService<User>{
	
	/**
	 * @param username
	 * @return
	 */
	User getObj(String username);
	
	/**
	 * @param username
	 * @return
	 */
	String getSecretkey(String username);

	/**
	 * @return
	 */
	int logout();

	/**
	 * @param enabled
	 */
	int setEnabled(boolean enabled);

	/**
	 * @param username
	 */
	int active(String username);

}
