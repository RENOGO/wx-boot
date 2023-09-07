package com.wx.common.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis工具类
 *
 * @author wx
 * 不需要DI注入这个类，直接当工具类使用，采用RedisUtil.function方式调用即可
 */
@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        redisUtil = this;
    }

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisUtil.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return redisUtil.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return redisUtil.redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisUtil.redisTemplate.delete(key[0]);
            } else {
                redisUtil.redisTemplate.delete(Arrays.asList(key));
            }
        }
    }


    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : redisUtil.redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入,key一样会覆盖
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */

    public static boolean set(String key, Object value) {
        try {
            redisUtil.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 普通缓存放入并设置时间,key一样会覆盖
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */

    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisUtil.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间,key一样会覆盖
     *
     * @param key   键
     * @param value 值
     * @param time  时间(单位自定义) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */

    public static boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisUtil.redisTemplate.opsForValue().set(key, value, time, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisUtil.redisTemplate.opsForValue().increment(key, delta);
    }


    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisUtil.redisTemplate.opsForValue().increment(key, -delta);
    }


    // ================================hash Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     */
    public static Object hget(String key, String item) {
        return redisUtil.redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        return redisUtil.redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            redisUtil.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisUtil.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * 实际用起来和hmset一样，但hmset被弃用，不要用了
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     * Map<String,Map<String, Object>>
     */
    public static boolean hset(String key, String item, Object value) {
        try {
            redisUtil.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value, long time) {
        try {
            redisUtil.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        redisUtil.redisTemplate.opsForHash().delete(key, item);
    }


    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return redisUtil.redisTemplate.opsForHash().hasKey(key, item);
    }


    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public static double hincr(String key, String item, double by) {
        return redisUtil.redisTemplate.opsForHash().increment(key, item, by);
    }


    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public static double hdecr(String key, String item, double by) {
        return redisUtil.redisTemplate.opsForHash().increment(key, item, -by);
    }


    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisUtil.redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return redisUtil.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            return redisUtil.redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisUtil.redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public static long sGetSetSize(String key) {
        try {
            return redisUtil.redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */

    public static long setRemove(String key, Object... values) {
        try {
            Long count = redisUtil.redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisUtil.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public static long lGetListSize(String key) {
        try {
            return redisUtil.redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisUtil.redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过key取出值
     *
     * @param key
     * @return
     */
    public static Object lGet(String key) {
        try {
            return redisUtil.redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public static boolean lSet(String key, Object value) {
        try {
            redisUtil.redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisUtil.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */

    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisUtil.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */

    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisUtil.redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    //===================================

    /**
     * 根据匹配条件，列出所有带有这个条件的key，如test:*，可以返回test:value1,test:value2等key
     *
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        return redisUtil.redisTemplate.keys(pattern);
    }

    /**
     * 根据匹配条件，列出所有带有这个条件的key，如test:*，可以返回test:value1,test:value2等key
     * 然后一次清除这些数据
     *
     * @param pattern
     * @return
     */
    public static void delKeyByPattern(String pattern) {
        Set<String> keys = keys(pattern);
        redisUtil.redisTemplate.delete(keys);
    }

    /**
     * 往redis消息队列发送消息
     *
     * @param channel
     * @param message
     */
    public static void convertAndSend(String channel, Object message) {
        redisUtil.redisTemplate.convertAndSend(channel, message);
    }

    //===================zset===================
    // Map<String,Map<String, Double>>  第一个str是key，第二个str是value，Double是得分
    // 以下所有按范围查询，如果value只有n个，传入超过n的大小，是不会报错的，会把n个value都返回

    /**
     * 增加有序集合
     * Map<String,Map<String, Double>>  Double是得分
     *
     * @param key   一个key可以对应多个value
     * @param value 同一个value可以重复传，只是得分会覆盖
     * @param seqNo value对应的初始得分
     * @return
     */
    public static Boolean zsetAdd(String key, Object value, double seqNo) {
        try {
            return redisUtil.redisTemplate.opsForZSet().add(key, value, seqNo);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取zset集合数量
     *
     * @param key
     * @return
     */
    public static Long zsetCount(String key) {
        try {
            return redisUtil.redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 获得zset所有内容，按分数由小到大排序
     *
     * @param key
     * @return 返回的Object是value，没有得分
     */
    public static Set<Object> zsetGetAsc(String key) {
        try {
            return redisUtil.redisTemplate.opsForZSet().range(key, 0, -1);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获得zset所有内容，按分数由小到大排序
     *
     * @param key
     * @return 返回的Object是value，没有得分
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zsetGetWithScoresAsc(String key) {
        try {
            return redisUtil.redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取zset指定范围内的集合,开始下标是0，分数由大到小排序
     * 起始是闭区间，即传入0和3，那么会将0~3，返回第一到第四名
     * 传入0，0就是第一名
     *
     * @param key
     * @param start
     * @param end
     * @return 返回value+得分
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zsetRangeWithScoresDesc(String key, long start, long end) {
        try {
            //这里的end要+1，和由小大大排序不一样
            return redisUtil.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, Double.MIN_VALUE, Double.MAX_VALUE, start, end + 1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取zset指定范围+得分范围的集合,开始下标是0，分数由大到小排序
     * 起始是闭区间，即传入0和3，那么会将0~3，返回第一到第四名
     * 传入0，0就是第一名
     * 得分也是闭区间，即传入1.0~4.0，会包含1.0和4.0
     *
     * @param key
     * @param start
     * @param end
     * @return 返回value+得分
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zsetRangeWithScoresDesc(String key, double scoreStart, double scoreEnd, long start, long end) {
        try {
            //这里的end是要+1
            return redisUtil.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, scoreStart, scoreEnd, start, end + 1);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取zset指定范围+得分范围的集合,开始下标是0，分数由小到大排序
     * 起始是闭区间，即传入0和3，那么会将0~3，返回第一到第四名
     * 传入0，0就是第一名
     * 得分也是闭区间，即传入1.0~4.0，会包含1.0和4.0
     *
     * @param scoreStart
     * @param scoreEnd
     * @param key
     * @param start
     * @param end
     * @return 返回value+得分
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zsetRangeWithScoresAsc(String key, double scoreStart, double scoreEnd, long start, long end) {
        try {
            return redisUtil.redisTemplate.opsForZSet().rangeByScoreWithScores(key, scoreStart, scoreEnd, start, end + 1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取zset指定范围内的集合,开始下标是0，分数由小到大排序
     * 起始是闭区间，即传入0和3，那么会将0~3，返回第一到第四名
     * 传入0，0就是第一名
     *
     * @param key
     * @param start
     * @param end
     * @return 返回value+得分
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zsetRangeWithScoresAsc(String key, long start, long end) {
        try {
            return redisUtil.redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取zset指定范围内的集合,开始下标是0，分数由小到大排序
     * 起始是闭区间，即传入0和3，那么会将0~3，返回第一到第四名
     * 传入0，0就是第一名
     *
     * @param key
     * @param start
     * @param end
     * @return 返回的Object是value，没有得分
     */
    public static Set<Object> zsetRangeAsc(String key, long start, long end) {
        try {
            return redisUtil.redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据key和value移除指定元素
     *
     * @param key
     * @param value
     * @return
     */
    public static Long zsetRemove(String key, Object value) {
        return redisUtil.redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 获取对应key和value的score
     *
     * @param key
     * @param value
     * @return
     */
    public static Double zsetScore(String key, Object value) {
        return redisUtil.redisTemplate.opsForZSet().score(key, value);
    }


    /**
     * 指定范围内元素排序[]默认都是闭区间，即传入分数0和100，包含0和100
     * 只会把分数在[0,100]之间的数据检索出来，按得分由小到大排列
     *
     * @param key
     * @param v1  这是得分，不是数组下标
     * @param v2  这是得分，不是数组下标
     * @return 返回的Object是value，没有得分
     */
    public static Set<Object> zsetRangeByScore(String key, double v1, double v2) {
        return redisUtil.redisTemplate.opsForZSet().rangeByScore(key, v1, v2);
    }

    /**
     * 指定元素增加指定值
     *
     * @param key
     * @param obj
     * @param score 加法传正值，减法传负值
     * @return
     */
    public static Object zSetAddScore(String key, Object obj, double score) {
        return redisUtil.redisTemplate.opsForZSet().incrementScore(key, obj, score);
    }


    /**
     * 元素在集合内的排名
     *
     * @param key
     * @param obj
     * @return 这里返回整型，0是第一名
     */
    public static Object zsetRank(String key, Object obj) {
        return redisUtil.redisTemplate.opsForZSet().rank(key, obj);
    }


}