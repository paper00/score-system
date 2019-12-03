package com.score.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.score.bean.Score;

public interface ScoreService2 extends IService<Score>{

	List<Score>  getScoreById(String studentNumber);
	Score getScoreByCourseId(String studentNumber,String courseId);
	List<Score> getScoreByCourseId(String courseId);
}
