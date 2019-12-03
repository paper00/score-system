package com.score.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.score.bean.Major;
import com.score.bean.Teacher;
import com.score.mapper.MajorMapper;
import com.score.mapper.TeacherMapper;
import com.score.service.MajorService;
import com.score.service.TeacherService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/12
 */
@Service
@Slf4j
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
	
	@Resource
	TeacherMapper teacherMapper;
	@Resource
	MajorService majorService;

	@Override
	public String getMajorByMajorid(String majorId) {
		return baseMapper.getMajorByMajorid(majorId);
	}

	@Override
	public Teacher getObj(String teacherId) {
		QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", teacherId);
		Teacher teacher = baseMapper.selectOne(queryWrapper);
		// 添加专业名
		Major major = majorService.getMajorById(teacher.getMajorid());
//		String major = getMajorByMajorid(teacher.getMajorid());
		teacher.setMajorName(major.getMajorName());
		return teacher;
	}

}