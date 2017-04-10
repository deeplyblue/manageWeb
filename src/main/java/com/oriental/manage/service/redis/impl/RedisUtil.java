package com.oriental.manage.service.redis.impl;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * @author  jim lin
 * Date: 2016-02-04 17:03
 */
@Slf4j
public class RedisUtil {
    private Pool jedisPool;
    public RedisUtil(Pool jedisPool){
        this.jedisPool = jedisPool;
    }

    /**
     * 获取数据库连接
     * @return
     */
    public Jedis getConnection() {
        Jedis jedis = null;
        try {
            //获取Jedis数据库连接
            jedis = (Jedis) jedisPool.getResource();
        } catch (Exception e) {
            log.debug("Jedis获取数据库连接出现异常", e);
        }
        return jedis;
    }
    /**
     * 关闭数据库连接
     * @param jedis
     */
    public void closeConnection(Jedis jedis) {
        if (null != jedis) {
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
                log.debug("Jedis关闭数据库连接出现异常", e);

            }
        }
    }

    /**
     * 关闭数据库连接
     * @param jedis
     */
    public void closeBrokenConnection(Jedis jedis){
        if (null != jedis) {
            try {
                jedisPool.returnBrokenResource(jedis);
            } catch (Exception e) {
                log.debug("Jedis释放无效的连接异常", e);

            }
        }
    }
    
}
