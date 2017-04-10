package com.oriental.manage.core.system;

import com.oriental.manage.core.enums.RedisKey;
import com.oriental.manage.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by lupf on 2016/6/28.
 */
@Slf4j
@Repository("ConstantsRedis")
@Scope
public class ConstantsRedis {

    @Autowired
    private RedisService redisServiceImpl;


    public void init(RedisKey redisKey, Map<String, String> map) {
        log.debug("刷新redis缓存:{}", redisKey.getDesc());
        redisServiceImpl.setHashMap(redisKey.getHashKey(), map);
    }
}
