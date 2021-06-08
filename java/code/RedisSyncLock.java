package net.xuele.course.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis同步锁
 */
@Service
public class RedisSyncLock {

    @Autowired()
    @Qualifier("codis")
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取锁
     * @param key 锁关键词 锁ID
     * @param expireTime 到期后自动释放锁 毫秒
     * @return true 获取成功/ false 获取失败
     */
    public boolean getLock(String key, long expireTime){
        BoundValueOperations<String, String> operations = redisTemplate.boundValueOps(key);
        if(operations.getAndSet("l")==null){
            operations.expire(expireTime, TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }

    /**
     * 获取锁
     * @param key 锁关键词 锁ID
     * @param expireTime 到期后自动释放锁 毫秒
     * @param pollInterval 轮询加锁间隔时间 毫秒
     */
    public void getLockWithBlockPolling(String key, long expireTime,long pollInterval){
        BoundValueOperations<String, String> operations = redisTemplate.boundValueOps(key);
        while (operations.getAndSet("l")!=null){
            try {
                Thread.sleep(pollInterval);
            } catch (InterruptedException ignored) {}
        }
        operations.expire(expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 释放锁
     * @param key 锁关键词
     */
    public void release(String key){
        redisTemplate.delete(key);
    }

}
