package com.score.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.score.bean.Course;

/**
 * @author paper
 * @date 2019/11/12
 * @description TODO
 */
public interface CourseService {

	/**
	 * @param courseId
	 * @return
	 */
	String getTeacherIdByCourseId(String courseId);

	/**
	 * @return
	 */
	Map<String, Object> getAllCourses();

	/**
	 * @param course
	 */
	int insert(Course course);

	/**
	 * @param course
	 */
	int update(Course course);

	/**
	 * @param courseId 
	 * @return
	 */
	Course getObj(String courseId);

	/**
	 * @param teacherId
	 */
	Map<String, Object> getCoursesByTeacherId(String teacherId);

}
