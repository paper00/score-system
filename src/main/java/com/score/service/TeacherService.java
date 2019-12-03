package com.score.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.score.bean.Teacher;

/**
 * @author paper
 * @date 2019/11/12
 * @description TODO
 */
public interface TeacherService extends IService<Teacher>{

	/**
	 * @param majorId 
	 * @return
	 */
	String getMajorByMajorid(String majorId);

	/**
	 * @param teacherId
	 * @return
	 */
	Teacher getObj(String teacherId);

}
