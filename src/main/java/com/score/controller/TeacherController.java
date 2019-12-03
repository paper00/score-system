package com.score.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.score.service.CourseService;
import com.score.service.ScoreService;
import com.score.service.TeacherService;
import com.score.utils.Result;

import lombok.extern.slf4j.Slf4j;

import com.score.bean.Score;
import com.score.bean.Teacher;

/**
 * @author paper
 * @date 2019/11/12
 */
@RestController
@RequestMapping(("teacher/api"))
@Slf4j
public class TeacherController {
	
	@Resource
	ScoreService scoreService;
	@Resource
	CourseService courseService;
	@Resource
	TeacherService teacherService;
	
	/**
	 * 查看排名
	 * @param courseId
	 * @return
	 */
	@GetMapping("scores/{courseId}")
	public Object getScores(@PathVariable String courseId) {
		if (!hasPermission(courseId)) {
			return Result.UserError().appendInfo("用户权限不足");
		}
		Map<String, Object> scores = scoreService.getScoresByCourseId(courseId);
		if (scores != null) {
			scores.put("average", scoreService.getAvgScore(courseId));
			return Result.OK().appendInfo(scores);
		}
		log.warn("不存在对应的信息");
		return Result.UserError().appendInfo("不存在对应的信息");
	}
	
	/**
	 * 查看平均分
	 * @param courseId
	 * @return
	 */
	@GetMapping("average/{courseId}")
	public Object getAverage(@PathVariable String courseId) {
		if(!hasPermission(courseId)) {
			return Result.UserError().appendInfo("用户权限不足");
		}
		Double avg = scoreService.getAvgScore(courseId);
		if(avg >= 0) {
			return Result.OK().appendInfo(avg);
		}
		log.warn("不存在对应的信息");
		return Result.UserError().appendInfo("不存在对应的信息");
	}
	
	/**
	 * 查看教师信息
	 * @return
	 */
	@GetMapping("data")
	public Object getData() {
		String teacherId = SecurityContextHolder.getContext().getAuthentication().getName();
		Teacher teacher = teacherService.getObj(teacherId);
		if (teacher != null) {
			return Result.OK().appendInfo(teacher);
		}
		log.warn("获取不到教师信息");
		return Result.ServerError().appendInfo("系统繁忙，稍后重试");
	}
	
	/**
	 * 添加学生成绩
	 * @param score
	 * @return
	 */
	@PostMapping("addscore")
	public Object addScore(@RequestBody Score score) {
		if (scoreService.insert(score) > 0) {
			return Result.OK().appendInfo("[已插入一条学生成绩] " + score);
		}
		log.error("插入失败");
		return Result.ServerError().appendInfo("插入失败");
	}
	
	/**
	 * 更新学生成绩
	 * @param score
	 * @return
	 */
	@PostMapping("updatescore")
	public Object updateScore(@RequestBody Score score) {
		if (scoreService.update(score) > 0) {
			return Result.OK().appendInfo("[已更新一条学生成绩] " + score);
		}
		log.error("更新失败");
		return Result.UserError().appendInfo("更新失败");
	}
	
	/**
	 * 查看教师的任课信息
	 * @return
	 */
	@GetMapping("courses")
	public Object getCourses() {
		Map<String, Object> map = courseService.getCoursesByTeacherId(getUserId());
		if ((int) map.get("total") > 0) {
			return Result.OK().appendInfo(map);
		}
		return Result.UserError().appendInfo("该教师无任课信息");
	}
	
	/**
	 * 判断是否拥有权限
	 * @param courseId
	 * @return
	 */
	public boolean hasPermission(String courseId) {
		String teacherId = getUserId();
		String temp = courseService.getTeacherIdByCourseId(courseId);
		if (teacherId.equals(temp)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取当前用户的ID
	 * @return
	 */
	public String getUserId() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
}