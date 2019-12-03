package com.score.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.score.bean.Course;
import com.score.bean.Score;
import com.score.mapper.ScoreMapper;
import com.score.service.ScoreService2;

@Service
public class ScoreServiceImpl2 extends ServiceImpl<ScoreMapper, Score> implements ScoreService2{

	@Autowired
	private ScoreMapper scoreMapper;	
	@Override
	public List<Score>  getScoreById(String studentNumber) {						
		QueryWrapper<Score> queryWrapper = new QueryWrapper<Score>();	
		queryWrapper.eq("studentNumber", studentNumber);
		queryWrapper.orderByAsc("studentNumber");
		Page<Score> page =new Page<>(1,5);
		IPage<Score> IPage = baseMapper.selectPage(page, queryWrapper);
		List<Score>records = IPage .getRecords();
		return records;
			
	}
	/*	Map<String, Object> columnMap = new HashMap<>();
	  	columnMap.put("studentNumber", studentNumber);
		return baseMapper.selectByMap(columnMap) ;			*/
	
	@Override
	public Score  getScoreByCourseId(String studentNumber,String courseId) {			
		QueryWrapper<Score> queryWrapper = new QueryWrapper<Score>();	
		queryWrapper.eq("studentNumber", studentNumber);
		queryWrapper.eq("courseId", courseId);
		return baseMapper.selectOne(queryWrapper) ;	
	}

	@Override
	public List<Score> getScoreByCourseId(String courseId) {
		QueryWrapper<Score> queryWrapper = new QueryWrapper<Score>();	
		queryWrapper.eq("courseId", courseId);
		queryWrapper.orderByAsc("courseId");
		Page<Score> page =new Page<>(1,5);
		IPage<Score> IPage = baseMapper.selectPage(page, queryWrapper);
		List<Score>records = IPage .getRecords();
		return records;	
		/*Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("courseId", courseId);
		return baseMapper.selectByMap(columnMap) ;	*/
	}

}
