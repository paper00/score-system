package com.score.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.score.bean.TC;
import com.score.mapper.TCMapper;
import com.score.service.TCService;

/**
 * @author paper
 * @date 2019/11/16
 */
@Service
public class TCServiceImpl extends ServiceImpl<TCMapper, TC> implements TCService {

	@Override
	public List<TC> getObjs(String teacherId) {
		QueryWrapper<TC> queryWrapper = new QueryWrapper<TC>();
		queryWrapper.eq("teacherId", teacherId);
		return baseMapper.selectList(queryWrapper);
	}

}
