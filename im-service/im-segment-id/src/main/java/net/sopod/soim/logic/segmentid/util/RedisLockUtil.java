package net.sopod.soim.logic.segmentid.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁工具
 *
 * @author tangmingyou
 * @date 2022-03-18 14:21
 */
public class RedisLockUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisLockUtil.class);

    private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript<>("return redis.call('set',KEYS[1],ARGV[1],'NX','PX',ARGV[2])", String.class);
    private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript<>("if redis.call('get',KEYS[1]) == ARGV[1] then return tostring(redis.call('del', KEYS[1])==1) else return 'false' end", String.class);
    private static final String LOCK_SUCCESS = "OK";

    /**
     * 加锁，往 redis 中设置值并设置过期时间
     * @param lockKey 键
     * @param lockValue 值
     * @param acquireExpire 超期时间(毫秒)
     * @return 是否获取成功
     */
    public static boolean acquireLock(RedisTemplate<String, String> redisTemplate,
                                      String lockKey, String lockValue, long acquireExpire) {
        Object lockResult = redisTemplate.execute(SCRIPT_LOCK,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(lockKey),
                lockValue, String.valueOf(acquireExpire));
        // 加锁失败 lockResult 为 null
        return LOCK_SUCCESS.equals(lockResult);
    }

    /**
     * <pre>
     * 为何解锁需要校验lockValue
     * 客户端A加锁，一段时间之后客户端A解锁，在执行releaseLock之前，锁突然过期了。
     * 此时客户端B尝试加锁成功，然后客户端A再执行releaseLock方法，则将客户端B的锁给解除了。
     * </pre>
     * @param lockKey redis 键
     * @param lockValue lock 的值
     * @return 是否成功
     */
    public static boolean releaseLock(RedisTemplate<String, String> redisTemplate,
                                      String lockKey, String lockValue) {
        Object releaseResult = redisTemplate.execute(SCRIPT_UNLOCK,
                redisTemplate.getStringSerializer(),
                redisTemplate.getStringSerializer(),
                Collections.singletonList(lockKey),
                lockValue);
        return Boolean.parseBoolean(releaseResult.toString());
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return 是否成功
     */
    public static boolean expire(RedisTemplate<String, String> redisTemplate,
                                 String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("指定缓存失效时间异常");
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(RedisTemplate<String, String> redisTemplate, String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(RedisTemplate<String, String> redisTemplate, String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除缓存
     * @param keys 可以传一个值 或多个
     */
    public static void del(RedisTemplate<String, String> redisTemplate, String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(Arrays.asList(keys));
            }
        }
    }

}
