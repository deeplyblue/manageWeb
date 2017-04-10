package com.oriental.manage.service.redis.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.util.Pool;

/**
 * @author  jim lin
 * Date: 2016-02-04 17:03
 */
@Service
@Slf4j
public class RedisServiceImpl extends BaseRedisImpl {
    @Autowired
    public RedisServiceImpl(Pool jedisPool) {
        super();
        super.redisUtil = new RedisUtil(jedisPool);
    }

    public static void main(String[] args) {


    }
}
