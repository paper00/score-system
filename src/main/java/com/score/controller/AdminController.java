package com.score.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.score.bean.Course;
import com.score.bean.Score;
import com.score.service.CourseService;
import com.score.service.ScoreService;
import com.score.service.TeacherService;
import com.score.utils.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * @author paper
 * @date 2019/11/12
 */
@RestController
@RequestMapping(("admin/api"))
@Slf4j
public class AdminController {
	
	@Resource
	ScoreService scoreService;
	@Resource
	CourseService courseService;
	@Resource
	TeacherService teacherService;
	
	/**
	 * 查看所有课程
	 * @return
	 */
	@GetMapping("courses")
	public Object getCourses() {
		Map<String, Object> courses = courseService.getAllCourses();
		if (courses != null) {
			return Result.OK().appendInfo(courses);
		}
		log.warn("不存在对应的信息");
		return Result.UserError().appendInfo("不存在对应的信息");
	}
	
	/**
	 * 查看课程
	 * @param courseId
	 * @return
	 */
	@GetMapping("course/{courseId}")
	public Object getCourseByCourseId(@PathVariable String courseId) {
		Course course = courseService.getObj(courseId);
		if (course != null) {
			return Result.OK().appendInfo(course);
		}
		log.warn("不存在对应的信息");
		return Result.UserError().appendInfo("不存在对应的信息");
	}
	
	/**
	 * 添加新课程
	 * @param course
	 * @return
	 */
	@PostMapping("addcourse")
	public Object addCourse(@RequestBody Course course) {
		if (courseService.insert(course) > 0) {
			return Result.OK().appendInfo("[已插入一门新课程] " + course);
		}
		log.error("插入失败");
		return Result.ServerError().appendInfo("插入失败");
	}
	
	/**
	 * 更新课程信息
	 * @param course
	 * @return
	 */
	@PostMapping("updatecourse")
	public Object updateCourse(@RequestBody Course course) {
		if (courseService.update(course) > 0) {
			return Result.OK().appendInfo("[已更新课程信息] " + course);
		}
		log.error("更新失败");
		return Result.ServerError().appendInfo("更新失败");
	}
	
	/**
	 * 查看成绩
	 * @param courseId
	 * @param studentNumber
	 * @return
	 */
	@GetMapping("score/{courseId}/{studentNumber}")
	public Object getScore(@PathVariable String courseId,
						   @PathVariable String studentNumber) {
		Score score = scoreService.getScoreByCondition(courseId, studentNumber);
		if (score != null) {
			return Result.OK().appendInfo(score);
		}
		log.warn("不存在对应的信息");
		return Result.UserError().appendInfo("不存在对应的信息");
	}
	
}
