package com.score.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.score.bean.Course;

/**
 * @author paper
 * @date 2019/11/12
 */
public interface CourseMapper extends BaseMapper<Course> {
	
	/**
	 * @param courseId
	 * @return
	 */
	String getTeacherIdByCourseId(@Param("courseId") String courseId);
	
}
