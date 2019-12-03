package com.score.service.impl;

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
import com.score.bean.Score;
import com.score.bean.Student;
import com.score.exception.UserException;
import com.score.mapper.ScoreMapper;
import com.score.service.CourseService;
import com.score.service.ScoreService;
import com.score.service.StudentService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/12
 */
@Service
@Slf4j
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
	
	@Resource
	ScoreMapper scoreMapper;
	@Resource
	CourseService courseService;
	@Resource
	StudentService studentService;

	@Override
	public Map<String, Object> getScoresByCourseId(String courseId) {
		Map<String, Object> map = new HashMap<>();
		QueryWrapper<Score> queryWrapper =  new QueryWrapper<>();
		queryWrapper.eq("courseId", courseId);
		queryWrapper.orderByDesc("total");
		Page<Score> page = new Page<>(1,5);		// 当前页1，每页5条记录
		IPage<Score> iPage = baseMapper.selectPage(page, queryWrapper);
		map.put("total", iPage.getTotal());
		map.put("size", iPage.getSize());
		map.put("current", iPage.getCurrent());
		map.put("pages", iPage.getPages());
		// 设置课程名，学生名和平均分
		List<Score> scores = baseMapper.selectList(queryWrapper);
		Course course = courseService.getObj(courseId);
		for (int i=0; i<scores.size(); i++) {
			scores.get(i).setCourseName(course.getCourseName());
			Student student = studentService.getStudentById(scores.get(i).getStudentNumber());
			scores.get(i).setStudentName(student.getName());
		}
		map.put("scores", scores);
		return map;
	}

	@Override
	public Double getAvgScore(String courseId) {
		List<Score> scores = (List<Score>) getScoresByCourseId(courseId).get("scores");
		int length = scores.size();
		if (length == 0) {
			return -1.;
		}
		Double sum = 0.;
		for (Score score : scores) {
			sum += score.getTotal();
		}
		return (double) Math.round(sum/length * 100) / 100;
	}

	@Override
	public int insert(Score score) {
		int count = baseMapper.insert(score);
		if (count > 0) {
			log.info("[插入一条学生成绩] " + score.toString());
			return count;
		}
		log.error("插入失败");
		return 0;
	}

	@Override
	public int update(Score score) {
		QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("studentNumber", score.getStudentNumber());
		queryWrapper.eq("courseId", score.getCourseId());
		int count = baseMapper.update(score, queryWrapper);
		if (count > 0) {
			log.info("[更新一条学生成绩] " + score.toString());
			return count;
		}
		log.error("更新失败");
		return 0;
	}

	@Override
	public Score getScoreByCondition(String courseId, String studentNumber) {
		QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("courseId", courseId);
		queryWrapper.eq("studentNumber", studentNumber);
		Score score = baseMapper.selectOne(queryWrapper);
		// 设置课程名和学生名
		Course course = courseService.getObj(courseId);
		if (course != null && score != null) {
			score.setCourseName(course.getCourseName());
			Student student = studentService.getStudentById(studentNumber);
			score.setStudentName(student.getName());
		} else {
			log.error("不存在对应的信息");
		}
		return score;
	}
}
