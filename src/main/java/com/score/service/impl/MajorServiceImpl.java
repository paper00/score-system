package com.score.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.score.bean.Major;
import com.score.mapper.MajorMapper;
import com.score.service.MajorService;


@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper,Major> implements MajorService {

	@Override
	public Major getMajorById(String id) {
		QueryWrapper<Major> queryWrapper = new QueryWrapper<Major>();		
		queryWrapper.eq("id", id);
		return  baseMapper.selectOne(queryWrapper);
	}

	@Override
	public Major getMajorByName(String majorName) {
		QueryWrapper<Major> queryWrapper = new QueryWrapper<Major>();		
		queryWrapper.eq("majorName", majorName);
		return  baseMapper.selectOne(queryWrapper);
	}

	@Override
	public List<Major> getMajor() {
		QueryWrapper<Major> queryWrapper = new QueryWrapper<Major>();		
		queryWrapper.orderByAsc("majorName");
		List<Major> list= baseMapper.selectList(queryWrapper);
		return list;
	}
	
	

}
