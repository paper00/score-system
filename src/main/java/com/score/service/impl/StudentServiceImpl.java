package com.score.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.score.bean.*;

import com.score.mapper.StudentMapper;
import com.score.service.StudentService;

@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
	
		@Autowired
		private  StudentMapper studentMapper;

		@Override
		public Student getStudentById(String id) {			
			QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
			queryWrapper.eq("number",id);
			return  baseMapper.selectOne(queryWrapper);			
		}
		@Override
		public  List<Student> getStudentByMajor(String majorId,Integer year) {
			Map<String, Object> columnMap = new HashMap<>();
			columnMap.put("majorId", majorId);
			columnMap.put("year", year);
			return baseMapper.selectByMap(columnMap) ;			
		}		
		
	}

