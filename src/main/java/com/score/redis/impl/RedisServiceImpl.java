package com.score.redis.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.score.redis.IRedisService;

/**
 * @author paper
 * @date 2019/11/14
 */
public class RedisServiceImpl implements IRedisService {

	@Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getValue(String key) {
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        return vo.get(key);
    }

    @Override
    public void setValue(String key, String value) {
        ValueOperations<String, String> vo = stringRedisTemplate.opsForValue();
        vo.set(key, value);
        stringRedisTemplate.expire(key, 1, TimeUnit.HOURS); // 1小时后失效
    }
    
}
