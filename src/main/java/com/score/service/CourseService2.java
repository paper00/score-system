package com.score.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.score.bean.Course;
@Service
public interface CourseService2 extends IService<Course>{

	List<Course> getCourseByType(String type);
	List<Course> getCourseByTerm(String term);
	List<Course> getCourseByTAT(String type,String term);
	Course getCourseById(String id);
	Course getCourseByName(String courseName);

}
