package il.fridman.tempus.redis;

import il.fridman.tempus.aspect.Loggable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Loggable
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    public void setHashValue(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object getHashValue(String key, String hashKey) {
        if (redisTemplate.opsForHash().hasKey(key, hashKey)) {
            return redisTemplate.opsForHash().get(key, hashKey);
        }
        return null;
    }

    public void deleteHashValue(String key) {
        redisTemplate.opsForHash().delete(key);
    }
}