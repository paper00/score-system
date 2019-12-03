package com.score.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.score.bean.Student;

public interface StudentService extends IService<Student> {

	Student getStudentById(String id);
	List<Student> getStudentByMajor(String majorId,Integer year);
	
}
