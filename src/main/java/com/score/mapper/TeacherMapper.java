package com.score.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.score.bean.Teacher;

/**
 * @author paper
 * @date 2019/11/12
 */
public interface TeacherMapper extends BaseMapper<Teacher>{
	
	/**
	 * @param majorId
	 * @return
	 */
	String getMajorByMajorid(@Param("majorId") String majorId);
	
}
