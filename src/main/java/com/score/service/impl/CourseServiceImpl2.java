package com.score.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.score.bean.Course;
import com.score.mapper.CourseMapper;
import com.score.service.CourseService2;

@Service
public class CourseServiceImpl2 extends ServiceImpl<CourseMapper, Course> implements CourseService2{

	@Override
	public List<Course> getCourseByType(String type) {	
		Map<String, Object> columnMap = new HashMap<>();	
		columnMap.put("type", type);
		List<Course> ListC = baseMapper.selectByMap(columnMap);		
		return ListC;
	}

	@Override
	public Course getCourseById(String id) {		
		QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();	
		queryWrapper.eq("id", id);
		return  baseMapper.selectOne(queryWrapper);		
		
	}
	
	@Override
	public List<Course> getCourseByTerm(String term) {		
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("term", term);
		return baseMapper.selectByMap(columnMap);
	}

	@Override
	public Course getCourseByName(String courseName) {
		QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();		
		queryWrapper.eq("courseName", courseName);
		return  baseMapper.selectOne(queryWrapper);		
		
	}

	@Override
	public List<Course> getCourseByTAT(String type, String term) {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("term", term);
		columnMap.put("type", type);
		return baseMapper.selectByMap(columnMap);
	}
}
