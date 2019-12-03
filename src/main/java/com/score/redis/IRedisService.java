package com.score.redis;

import java.util.Map;

/**
 * @author paper
 * @date 2019/11/14
 * @description 操作 Redis
 */
public interface IRedisService {
	
	String getValue(String key);
//	Object getObjectValue(String key);
//	Object getMapValue(String key);
	void setValue(String key, String value);
//	void setValue(String key, Object value);
//  void setValue(String key, Map<String, Object> value);
    
}
