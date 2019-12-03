/**
 * 
 */
package com.score.service;

import java.util.List;
import java.util.Map;

import com.score.bean.Score;
import com.score.exception.UserException;

/**
 * @author paper
 * @date 2019/11/12
 */
public interface ScoreService {

	/**
	 * @param courseId
	 * @return 
	 */
	Map<String, Object> getScoresByCourseId(String courseId);

	/**
	 * @param courseId
	 * @return
	 */
	Double getAvgScore(String courseId);

	/**
	 * @param score
	 */
	int insert(Score score);

	/**
	 * @param score
	 */
	int update(Score score);

	/**
	 * @param courseId
	 * @param studentNumber
	 * @return
	 * @throws UserException 
	 */
	Score getScoreByCondition(String courseId, String studentNumber);

}
