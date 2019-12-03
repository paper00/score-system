package com.score.test.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author paper
 * @date 2019/11/14
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {
	
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void connection() {
    	stringRedisTemplate.opsForValue().set("test","111");
    }

}
