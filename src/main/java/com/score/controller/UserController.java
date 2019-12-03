package com.score.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.score.service.UserService;
import com.score.utils.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/14
 */
@RestController
@Slf4j
public class UserController {
	
	@Resource
	UserService userService;
	
	@GetMapping("aaa")
	public String aaa() {
		return "aaa";
	}
	
	@GetMapping("{role}/logout")
	public Object getScores(@PathVariable String role) {
		if (userService.logout() > 0) {
			return Result.OK().appendInfo("退出成功");
		}
		log.error("退出失败");
		return Result.ServerError().appendInfo("系统繁忙，稍后再试");
	}
	
}
