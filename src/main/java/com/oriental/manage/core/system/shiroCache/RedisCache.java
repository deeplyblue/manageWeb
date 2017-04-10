package com.oriental.manage.core.system.shiroCache;

import com.oriental.manage.service.redis.RedisService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * Created by lupf on 2016/12/6.
 */
public class RedisCache<K, V> implements Cache<K, V> {

    /**
     * redis的操作服务
     */
    private RedisService redisService;
    /**
     * redis键值前缀
     */
    private String prefix;

    /**
     * 构造redisCache
     *
     * @param name         键值前缀
     * @param redisService
     */
    public RedisCache(final String name, final RedisService redisService) {
        super();
        this.prefix = name;
        this.redisService = redisService;
    }

    @Override
    public Object get(Object key) throws CacheException {
        return null;
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        return null;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set keys() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }
}
