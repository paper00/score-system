/**
 * 
 */
package com.score.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.score.bean.TC;

/**
 * @author paper
 * @date 2019/11/16
 * @description TODO
 */
public interface TCService {

	/**
	 * @param teacherId
	 * @return
	 */
	List<TC> getObjs(String teacherId);

}
