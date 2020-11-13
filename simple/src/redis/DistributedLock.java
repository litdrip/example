package redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * 分布锁
 * 特性：
 * 1.加锁时，同时写过期时间，避免加锁后异常退出(如：断电)，导致永久锁。
 * 2.业务请求id标识，避免多任务进来后，获取锁的任务处理过长超过过期时间，待退出时可能会释放其它任务的锁。
 *
 * @author sk.z
 */
public class DistributedLock{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取锁
     * @param key 键
     * @param requestId 业务id 避免解锁其它任务的加锁
     * @param expireTime 过期时间 秒
     * @return true/false
     */
    public boolean lock(String key, String requestId, long expireTime) {
        Boolean b = redisTemplate.execute((RedisCallback<Boolean>) redisConnection ->
                redisConnection.set(key.getBytes(),
                        requestId.getBytes(),
                        Expiration.seconds(expireTime),
                        RedisStringCommands.SetOption.SET_IF_ABSENT));
        if (Objects.isNull(b)) {
            return false;
        }
        return b;
    }

    /**
     * 解锁
     * @param key 键
     * @param requestId 业务di 避免解锁其它任务的加锁
     * @return true/false
     */
    public boolean release(String key, String requestId) {
        Boolean b = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            byte[] bytes = connection.get(key.getBytes());
            if (Objects.isNull(bytes)) {
                return false;
            }
            connection.del(key.getBytes());
            return Arrays.equals(bytes, requestId.getBytes());
        });
        if (Objects.isNull(b)) {
            return false;
        }
        return b;
    }
}
