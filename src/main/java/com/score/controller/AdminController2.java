package com.score.controller;

import com.score.controller.StudentController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.score.bean.Major;
import com.score.bean.Score;
import com.score.bean.Student;
import com.score.service.CourseService2;
import com.score.service.MajorService;
import com.score.service.ScoreService2;
import com.score.service.StudentService;
import com.score.utils.Result;

@RestController
@RequestMapping("admin/api")
public class AdminController2 {

	@Resource
	private StudentService studentService;
	@Resource
	private ScoreService2  scoreService;
	@Resource
	private CourseService2 courseService;
	@Resource 
	private MajorService majorService;
	
//管理员查看所有的专业
	@GetMapping("/majors")
	public Object majors() {
		List<Major> list = majorService.getMajor();
		List<String> listS = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			listS.add(list.get(i).getMajorName());
		}
		
		return Result.OK().appendInfo(listS);
	}
	
//管理员查看全部成绩排名以及对应的GPA
	@GetMapping("/scores/{majorName}/{year}")
	public Object score(@PathVariable String majorName,@PathVariable Integer year){					
		String majorId= majorService.getMajorByName(majorName).getId();
		//lsitS 某个专业、某个年级的所有学生	
		List<Student> listS = studentService.getStudentByMajor(majorId,year);
		if(listS.isEmpty())
			return Result.ServerError().appendInfo("暂无排名");
		//listD 所有学生的所有成绩信息
		List<HashMap<Object,Object> > listD = new ArrayList<>();
		for (int i = 0; i < listS.size(); i++) {
			String studentId  = listS.get(i).getNumber();
			String studentName = listS.get(i).getName();
			List<Score> list = scoreService.getScoreById(studentId);
			if(list == null)
				continue;
			double GPA = 0;
			double total_credits = 0;
			double avg = 0 ;
			//计算个人GPA
			for (int j = 0; j < list.size(); j++) {
				String courseId =list.get(j).getCourseId();
				double credits = courseService.getCourseById(courseId).getCredits();
				double point = list.get(j).getPoint();
				double grade =list.get(i).getTotal();
				total_credits += credits;
				GPA += credits*point;
				avg += point*grade;
			}
			if(total_credits==0) {
				GPA = 0;
				avg = 0;
			}				
			else {
				GPA = GPA/total_credits;
				avg = avg/total_credits/list.size()/0.6;
			}				
			HashMap<Object, Object > e  = new HashMap<Object, Object>();
			e.put("studentId", studentId);
			e.put("studentName", studentName);
			e.put("avg", avg);
			e.put("GPA",GPA );
			e.put("major",majorName);
			listD.add(e);
			listD = StudentController.SortList(listD);			
		}		
		for (int i = 0; i <listD.size(); i++) {
			listD.get(i).put("rank", i+1+"/"+listD.size());
		}
		
		return Result.OK().appendInfo(listD);		
	}

	//查询一个学生所有成绩以及单科排名
		@GetMapping("/scoreDetails/{id}")
		public Object scoreDetails( @PathVariable String id){	
			//所有课程的成绩结果
			List<Score> list = scoreService.getScoreById(id);
			List<Score> ListS = new ArrayList<Score>();				
			if(list.isEmpty())
				return Result.ServerError().appendInfo("暂无成绩");
			//每门课程的成绩，写入姓名和课程名
			for (int i = 0; i < list.size(); i++) {	
				String courseId = list.get(i).getCourseId();
				String courseName = courseService.getCourseById(courseId).getCourseName();
				Score s = scoreService.getScoreByCourseId(id, courseId);
				//设置课程名以及学生姓名
				s.setCourseName(courseName);
				s.setStudentName(studentService.getStudentById(id).getName());
				//该课程所有人的成绩
				List<Score> listS1= scoreService.getScoreByCourseId(courseId);		
				Collections.sort(listS1,new Comparator<Score>(){
		            public int compare(Score o1, Score o2) {            		
						return o2.getTotal().compareTo(o1.getTotal());
		            }
		        });
				int rank = 0;
				for (int j = 0; j < listS1.size(); j++) {
					if(listS1.get(j).getStudentNumber().equals(id))
						rank = j+1;
				}	
				s.setRank(rank+"/"+listS1.size());
				ListS.add(s);
			}			
			return Result.OK().appendInfo(ListS);
		}
			
}
