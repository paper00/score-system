package com.score.test.Service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.score.mapper.UserMapper;
import com.score.service.UserService;

/**
 * @author paper
 * @date 2019/11/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	
	@Resource
	UserService userService;
	
	@Test
	public void getObj() {
		String str = "2017214859";
		String secretkey = userService.getSecretkey(str);
		System.out.println(secretkey);
	}
	
}
