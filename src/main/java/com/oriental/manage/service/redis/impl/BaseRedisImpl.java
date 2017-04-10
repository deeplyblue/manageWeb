package com.oriental.manage.service.redis.impl;

import com.oriental.manage.pojo.redis.Response;
import com.oriental.manage.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jim lin
 *         Date: 2016-02-04 17:03
 */
@Service
@Slf4j
public abstract class BaseRedisImpl implements RedisService {
    protected RedisUtil redisUtil;

    /**
     * 设置
     *
     * @param key   保存的key
     * @param value 保存的值
     * @param time  有效时间(分钟)
     */
    @Override
    public String setCachesData(String key, String value, int time) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            String result = jedis.setex(key, time * 60, value);

            log.debug("Redis存储数据[key:{},value:{},result:{}", key, value, result);
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return e.getMessage();
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            String result = jedis.set(key, value);

            log.debug("Redis存储数据[key:{},value:{},result:{}", key, value, result);
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return e.getMessage();
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    /**
     * 获取Redis数据
     *
     * @param key 存储key
     * @return 数据
     */
    @Override
    public String getCachesData(String key) {
        Jedis jedis = null;
        try {
            log.debug("Redis获取数据[key:{}];{}", key, redisUtil);
            jedis = redisUtil.getConnection();
            String result = jedis.get(key);
            log.debug("result：{}", result);
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return e.getMessage();
        } finally {
            redisUtil.closeConnection(jedis);
        }

    }

    /**
     * 获取Redis数据有效时间
     *
     * @param key 存储key
     * @return 有效时间
     */
    @Override
    public Long getCachesActionTime(String key) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            log.debug("Redis查询数据有效期[key:{}]", key);
            return jedis.ttl(key);
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis获取有效期异常,{}", e);
            return 0L;
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    /**
     * 删除Redis数据
     *
     * @param key 存储key
     */
    @Override
    public Long delCachesData(String key) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            Long lo = jedis.del(key);
            log.debug("Redis删除数据[key:{},result：{}]", key, lo);
            return lo;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis删除异常", e);
            return 0L;
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public Object setCachesSet(String key, String[] values) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            Long result = jedis.sadd(key, values);
            log.debug("{},Redis存储数据[key:{},value:{}]", result, key, values);
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return "ERROR：" + e.getMessage();
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public String setHashMap(String key, Map<String, String> map) {
        log.debug("key：{},map:{}", key, map);
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            String result = jedis.hmset(key, map);
            log.debug("{},Redis存储数据[key:{},value:{}]", result, key, map);
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return "ERROR：" + e.getMessage();
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    public String setHashMap(String key, Map<String, String> map, int time) {
        log.debug("key：{},map:{},time：{}", key, map, time);
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            String result = jedis.hmset(key, map);
            int expireSecond = time * 60;
            jedis.expire(key, expireSecond);
            log.debug("setHashMap{},Redis存储数据[key:{},value:{},time：{}]", result, key, map, time);
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return "ERROR：" + e.getMessage();
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public String getHashMap(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            String result = jedis.hget(key, field);
            if ("nil".equals(result)) {//当此field不存在的时候返回nil
                result = "";
            }
            log.debug("getHashMap{},Redis存储数据[key:{},field:{}]", result, key, field);
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return "ERROR：" + e.getMessage();
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //设置map里面的key值
            Long result = jedis.hset(key, field, value);
            log.debug("hset设置结果{},Redis获得数据[key:{},field{}，value:{}]", result, key, field, value);
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis改变hash值出错", e);
        } finally {
            redisUtil.closeConnection(jedis);
        }
        return 1L;
    }

    @Override
    public Long hsetByTime(String key, String field, String value, int time) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //设置map里面的key值
            Long result = jedis.hset(key, field, value);
            log.debug("hset设置结果{},Redis获得数据[key:{},field{}，value:{}]", result, key, field, value);
            if (1 == result) {
                if (time <= 0) {
                    time = 3;
                }
                jedis.expire(key, time);
                log.debug("Redis setDataLock jedis.expire,[key:{},value:{},result:{},time:{}]", key, value, result, time);
            }
            return result;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis改变hash值出错", e);
        } finally {
            redisUtil.closeConnection(jedis);
        }
        return 1L;
    }

    @Override
    public Response<HashMap<String, String>> getHashMapAll(String key) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            Map<String, String> result = jedis.hgetAll(key);
            HashMap<String, String> result1 = new HashMap<>(result);
            log.debug("getHashMapAll{},Redis存储数据[key:{},value:{}]", result, key, result);
            return new Response<>(result1);
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return new Response<>("9999", e.getMessage());
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public Set<String> getCachesSet(String key) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            Set<String> set = jedis.smembers(key);
            log.debug("Redis存储数据[key:{},value:{}]", key, set);
            return set;
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return null;
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public Long setDeadlineByKey(String key, int time) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            return jedis.expire(key, time);
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return 0L;
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public Response<Boolean> exists(String key) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //存储数据并保存时间
            return new Response<Boolean>(jedis.exists(key));
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
            return new Response<Boolean>("9999", "ERROR：" + e.getMessage());
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    @Override
    public Response<Long> delHashMapByField(String redisKey, String... mapKey) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getConnection();
            //移除数据
            return new Response<Long>(jedis.hdel(redisKey, mapKey));
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis移除异常", e);
            return new Response<Long>("9999", "ERROR：" + e.getMessage());
        } finally {
            redisUtil.closeConnection(jedis);
        }
    }

    public Response<Boolean> setDataLock(String key, String value, int time) {
        Jedis jedis = null;
        Response<Boolean> result = new Response<Boolean>();
        try {
            jedis = redisUtil.getConnection();
            Long setResult = jedis.setnx(key, value);
            log.debug("Redis setDataLock jedis.setnx,[key:{},value:{},setResult:{}]", key, value, setResult);
            if (setResult == 1L) result.setResult(true);
            if (result.isSuccess()) {
                if (time <= 0) time = 3;
                jedis.expire(key, time);
                log.debug("Redis setDataLock jedis.expire,[key:{},value:{},result:{},time:{}]", key, value, setResult, time);
            }
        } catch (Exception e) {
            redisUtil.closeBrokenConnection(jedis);
            log.error("redis存储异常", e);
        } finally {
            redisUtil.closeConnection(jedis);
        }
        return result;
    }


}
