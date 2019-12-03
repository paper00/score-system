package com.score.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.score.bean.Major;

public interface MajorService extends IService<Major> {

	Major getMajorById(String id);
	Major getMajorByName(String majorName);
	List<Major> getMajor();
}
