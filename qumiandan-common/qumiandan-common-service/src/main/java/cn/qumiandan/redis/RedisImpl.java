package cn.qumiandan.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

@Component
@Service(interfaceClass = IRedisApi.class)
public class RedisImpl<T, O> implements IRedisApi {
	
	@Autowired
	private RedisTemplate<T, O> redisTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

 
	@Override
	public StringRedisTemplate getStringRedis() {
		return stringRedisTemplate;
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	@Override
	public <T, O> RedisTemplate<T, O> getRedis() {
		return (RedisTemplate<T, O>) redisTemplate;
	}


}
