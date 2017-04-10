package com.oriental.manage.service.redis;




import com.oriental.manage.pojo.redis.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jim lin
 *         Date: 2016-02-04 17:03
 *         redis服务类
 */
public interface RedisService {
    /***
     * 添加Redis数据库
     *
     * @param key   键
     * @param value 值
     * @param time  有效时间
     */
    String setCachesData(String key, String value, int time);

    /***
     * 将key和value对应。如果key已经存在了，它会被覆盖，而不管它是什么类型。
     *
     * @param key   键
     * @param value 值
     */
    String set(String key, String value);


    /***
     * 获取Redis数据库
     * 返回key的value。如果key不存在，返回特殊值nil。
     * 如果key的value不是string，就返回错误，因为GET只处理string类型的values。
     *
     * @param key 键
     */
    String getCachesData(String key);

    /***
     * 获取Redis数据有效时间
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     * 返回值
     * 当 key 不存在时，返回 -2 。
     * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
     * 否则，以秒为单位，返回 key 的剩余生存时间。
     *
     * @param key 键
     * @return 有效时间
     */
    Long getCachesActionTime(String key);

    /***
     * 删除Redis数据库
     *
     * @param key 键
     */
    Long delCachesData(String key);


    /***
     * 添加一个或多个指定的member元素到集合的 key中.指定的一个或者多个元素member
     * 如果已经在集合key中存在则忽略.如果集合key 不存在，则新建集合key,并添加member元素到集合key中.
     * 如果key 的类型不是集合则返回错误.
     *
     * @param key    键
     * @param values 一个或者多个值
     * @return 返回新成功添加到集合里元素的数量，不包括已经存在于集合中的元素.
     */
    Object setCachesSet(String key, String[] values);

    /**
     * 设置hash字段值
     * 设置 key 指定的哈希集中指定字段的值。该命令将重写所有在哈希集中存在的字段。
     * 如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联
     *
     * @param key 键
     * @param map hashMap
     * @return 状态码
     */
    String setHashMap(String key, Map<String, String> map);

    /**
     * 设置hash字段值
     * 设置 key 指定的哈希集中指定字段的值。该命令将重写所有在哈希集中存在的字段。
     * 如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联
     *
     * @param key  键
     * @param map  hashmap
     * @param time 分钟
     * @return 状态码
     */
    String setHashMap(String key, Map<String, String> map, int time);

    /**
     * 读取哈希域的的值
     * 返回 key 指定的哈希集中该字段所关联的值
     *
     * @param key   键
     * @param field map的键
     * @return 该字段所关联的值。当字段不存在或者 key 不存在时返回nil。
     */
    String getHashMap(String key, String field);

    /**
     * 设置哈希域的的值
     *
     * @param key   key键
     * @param field hashmap里面的key值
     * @param value 对应的value
     * @return 如果field存在则为更新，返回0，否则创建返回1。
     */
    Long hset(String key, String field, String value);

    /**
     * 设置哈希域的的值
     *
     * @param key   key键
     * @param field hashmap里面的key值
     * @param value 对应的value
     * @param time  超时时间
     * @return 如果field存在则为更新，返回0，否则创建返回1。
     */
    Long hsetByTime(String key, String field, String value, int time);

    /**
     * 读取哈希域的的值
     * 返回 key 指定的哈希集中所有的字段和值。返回值中，每个字段名的下一个是它的值，所以返回值的长度是哈希集大小的两倍
     *
     * @param key 键
     * @return 哈希集中字段和值的列表。当 key 指定的哈希集不存在时返回空列表。
     */
    Response<HashMap<String, String>> getHashMapAll(String key);


    /***
     * 添加一个或多个指定的member元素到集合的 key中
     *
     * @param key 键
     * @return set 集合
     */
    Set<String> getCachesSet(String key);

    /***
     * 设置一个key的过期秒数
     *
     * @param key  键
     * @param time 过期时间（单位：秒）
     */
    Long setDeadlineByKey(String key, int time);

    /***
     * 查询一个key是否存在
     *
     * @param key 键
     * @return 返回key是否存在。如下的整数结果
     * <p>
     * 1 如果key存在
     * 0 如果key不存在
     */
    Response<Boolean> exists(String key);


    Response<Long> delHashMapByField(String redisKey, String... mapKey);


    /**
     * 设置数据锁
     *
     * @param key   锁的键
     * @param value 锁的值
     * @param time  锁的时间(s:秒)
     * @return true该key不存在并设置value|false该key存在不设置值
     */
    Response<Boolean> setDataLock(String key, String value, int time);

}
