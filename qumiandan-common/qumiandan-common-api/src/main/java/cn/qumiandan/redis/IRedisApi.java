package cn.qumiandan.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

public interface IRedisApi {

	/** 获取完整数据类型redis 
	 * @param <T>*/
	<T,O> RedisTemplate<T, O> getRedis();
	
	/** 获取简单key.value数据类型redis */
	StringRedisTemplate getStringRedis();
}
