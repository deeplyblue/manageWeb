package com.oriental.manage.core.system.shiroCache;

import com.oriental.manage.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lupf on 2016/11/16.
 *
 * shiro需要是要三类缓存
 * 1、认证授权信息
 * 2、session信息
 * 3、预定义的角色、权限信息(目前应该是空的)
 */
//@TODO 三类缓存需要根据配置，设定相应生存时间
@Slf4j
public class RedisCacheManager implements CacheManager {

    private final ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Autowired
    private RedisService redisService;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("获取名为:{}的缓存对象", name);
        Cache cache = caches.get(name);
        if (cache == null) {
            synchronized (this) {
                log.debug("create new cache, key:{}", name);
                cache = new RedisCache(name, redisService);
                caches.put(name, cache);
            }
        } else {
            log.debug("using existing cache, key:{}", name);
        }
        return cache;
    }
}
