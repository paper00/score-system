package com.score.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.score.bean.Course;
import com.score.bean.Score;
import com.score.bean.Student;
import com.score.service.CourseService2;
import com.score.service.MajorService;
import com.score.service.ScoreService2;
import com.score.service.StudentService;
import com.score.utils.Result;

@RestController
@RequestMapping("student/api")
public class StudentController {
	
	@Resource
	private StudentService studentService;
	@Resource
	private ScoreService2  scoreService;
	@Resource
	private CourseService2 courseService;
	@Resource 
	private MajorService majorService;
	
	
	//查询排名
	private String getRank(String courseId) {
		//该课程所有人的成绩
		List<Score> listS= scoreService.getScoreByCourseId(courseId);		
		Collections.sort(listS,new Comparator<Score>(){
            public int compare(Score o1, Score o2) {            		
				return o2.getTotal().compareTo(o1.getTotal());
            }
        });
		int rank = 0;
		for (int j = 0; j < listS.size(); j++) {
			if(listS.get(j).getStudentNumber().equals(getUserId()))
				rank = j+1;
		}			
		return rank+"/"+listS.size();	
	}
	
    //排序
	public static List<HashMap<Object, Object>> SortList(List<HashMap<Object, Object>> listD) {
		Collections.sort(listD, new Comparator<HashMap<Object, Object>>() {
		    public int compare(HashMap<Object, Object> o1, HashMap<Object, Object> o2) {
		    	Object key1 = null;
				Object key2 = null;
		    	for (Object key : o1.keySet()) {
		    		if(key.equals("GPA"))
            			key1 = key;
				}
		    	for (Object key : o2.keySet()) {
		    		if(key.equals("GPA"))
            			key2 = key;
				}	
		    	return String.valueOf(o2.get(key2)).compareTo(String.valueOf(o1.get(key1)));
		    }
		});
		return listD;
	}
	
	
	//查询所有成绩
	@GetMapping("/getScoreById")
	public Object getScoreById(){	
		//所有课程的成绩
		List<Score> list = scoreService.getScoreById(getUserId());
		List<Score> ListS = new ArrayList<Score>();			
		if(list.isEmpty())
			return Result.ServerError().appendInfo("暂无成绩");
		//每门课程的成绩，写入姓名和课程名
		for (int i = 0; i < list.size(); i++) {	
			String courseId = list.get(i).getCourseId();
			String courseName = courseService.getCourseById(courseId).getCourseName();
			Score s = scoreService.getScoreByCourseId(getUserId(), courseId);
			s.setCourseName(courseName);
			s.setStudentName(studentService.getStudentById(getUserId()).getName());						
			//计算每门课程的排名
			String rank = getRank(courseId);
			s.setRank(rank);
			ListS.add(s);
		}	
		return Result.OK().appendInfo(ListS);
	}

	//按照类型、以及学期查询成绩
	@GetMapping("/score/{type}/{term}")	
	public Object getScoreByTAT(@PathVariable  String type,@PathVariable  String term){
		List<Score> ListS = new ArrayList<Score>();		
		List<Course> ListC= courseService.getCourseByTAT(type,term);
		if(ListC.isEmpty())
			return Result.ServerError().appendInfo("暂无成绩");	
		
		for (int i = 0; i < ListC.size(); i++) {		
			String courseId = ListC.get(i).getId();	
			Score s = scoreService.getScoreByCourseId(getUserId(), courseId);
			if(s == null)
				return Result.ServerError().appendInfo("暂无成绩");	
			s.setCourseName(ListC.get(i).getCourseName());
			s.setStudentName(studentService.getStudentById(getUserId()).getName());
			//计算每门课程的排名
			String rank = getRank(courseId);
			s.setRank(rank);
			ListS.add(s);
		}		
		HashMap<Object,Object> m = getRankOfTerm(term);
		m.put("list", ListS);
		
		
		return Result.OK().appendInfo(m);
	}
	//查看某个学期个人成绩排名以及对应的GPA
	public HashMap<Object, Object> getRankOfTerm(@PathVariable String term){			
		Student s = studentService.getStudentById(getUserId());
		String majorId = s.getMajorId();
		Integer year = s.getYear();
		//lsitS 同个年级、同个专业的同学
		List<Student> listS = studentService.getStudentByMajor(majorId,year);		
		List<HashMap<Object,Object> > listD = new ArrayList<>();	
		for (int i = 0; i < listS.size(); i++) {
			String studentId  = listS.get(i).getNumber();
			List<Score> list = scoreService.getScoreById(studentId);
			if(list == null)
				continue;
			double GPA = 0;
			double total_credits = 0;
			double avg = 0;
			//计算某个学期内个人的GPA
			for (int j = 0; j < list.size(); j++) {
				boolean ok = false;
				//判断该课程是否是这个学期的课
				List<Course> ListC= courseService.getCourseByTerm(term);
				for (int k =0;k<ListC.size();k++) {
					if(ListC.get(k).getId().equals(list.get(j).getCourseId())) {
						ok = true;
						break;
					}						
				}
				if(ok == true)
				{
					String courseId =list.get(j).getCourseId();
					double credits = courseService.getCourseById(courseId).getCredits();
					double point = list.get(j).getPoint();
					double grade =list.get(j).getTotal();
					total_credits += credits;
					GPA += credits*point;
					avg += point*grade;
				}							
				else 		
					continue;
			}	
			if(total_credits ==0) {
				GPA = 0;avg = 0;
			}			
			else {
				GPA =GPA/total_credits;avg = avg/total_credits/list.size()/0.6;
			}				
			HashMap<Object, Object> e  = new HashMap<Object, Object>();
			if(total_credits ==0){
				e.put("GPA", 0.0);e.put("avg",0.0);
			}	
			else {
				e.put("GPA", GPA);e.put("avg",avg);
			}	
			e.put("studentId", getUserId());
			listD.add(e);	
			listD= SortList(listD);
		}
		
		int rank = 0;
		for (int i = 0; i < listD.size(); i++) {
			if(listD.get(i).get("studentId").equals(getUserId()))		
				rank = i+1;						
		}
		HashMap<Object,Object> m = new HashMap<Object,Object>();
		m.put("rank", rank+"/"+listD.size());
		m.put("GPA",listD.get(rank-1).get("GPA"));
		m.put("avg",listD.get(rank-1).get("avg"));
		return m;	

	}
	
	public String getUserId() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
}
