package com.shiftm.shiftm.domain.auth.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedisService {
	private final RedisTemplate<String, Object> redisTemplate;

	public void saveValues(String key, String data) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		values.set(key, data);
	}

	public void saveValues(String key, String data, Duration duration) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		values.set(key, data, duration);
	}

	@Transactional(readOnly = true)
	public String getValues(String key) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();

		if (values.get(key) == null) {
			return "false";
		}

		return (String) values.get(key);
	}

	public void deleteValues(String key) {
		redisTemplate.delete(key);
	}
}
