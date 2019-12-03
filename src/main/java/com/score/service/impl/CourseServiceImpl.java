package com.score.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.score.bean.Course;
import com.score.bean.TC;
import com.score.mapper.CourseMapper;
import com.score.mapper.TCMapper;
import com.score.service.CourseService;
import com.score.service.TCService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/12
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
	
	@Resource
	TCService tcService;
	@Resource
	CourseService courseService;
	
	@Override
	public String getTeacherIdByCourseId(String courseId) {
		return baseMapper.getTeacherIdByCourseId(courseId);
	}

	@Override
	public Map<String, Object> getAllCourses() {
		QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();
		queryWrapper.orderByDesc("term");
		List<Course> courses = baseMapper.selectList(queryWrapper);
		
//		Page<Course> page = new Page<>(1,10);
//		IPage<Course> iPage = baseMapper.selectPage(page, queryWrapper);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("IPage", iPage);
		
		Map<String, Object> map = new HashMap<String, Object>();
		int pages = (int) Math.ceil(courses.size()/5.);
		map.put("pages", pages);
		map.put("courses", courses);
		map.put("size", 5);
		map.put("current", 1);
		map.put("total", courses.size());
		return map;
	}

	@Override
	public int insert(Course course) {
		int count = baseMapper.insert(course);
		if (count > 0) {
			log.info("[插入一门新课程] " + course.toString());
			return count;
		}
		log.error("插入失败");
		return 0;
	}

	@Override
	public int update(Course course) {
		int count = baseMapper.updateById(course);
		if (count > 0) {
			log.info("[更新课程信息] " + course.toString());
			return count;
		}
		log.error("更新失败");
		return 0;
	}

	@Override
	public Course getObj(String courseId) {
		return baseMapper.selectById(courseId);
	}

	@Override
	public Map<String, Object> getCoursesByTeacherId(String teacherId) {
		List<Course> courses = new ArrayList<Course>();
		List<TC> tcs = tcService.getObjs(teacherId);
		for (TC tc : tcs) {
			courses.add(courseService.getObj(tc.getCourseId()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int pages = (int) Math.ceil(courses.size()/5.);
		map.put("pages", pages);
		map.put("courses", courses);
		map.put("size", 5);
		map.put("current", 1);
		map.put("total", courses.size());
		return map;
	}

}