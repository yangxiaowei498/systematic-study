package com.ethen.usecases;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * 案例 => redis实现分布式锁
 *
 * @see {https://wudashan.cn/2017/10/23/Redis-Distributed-Lock-Implement/}
 * <p>
 * note => 使用redis lua脚本保持原子操作
 * <p>
 * 分布式锁一般有三种实现方式：
 * =>1. 数据库乐观锁；
 * =>2. 基于Redis的分布式锁；
 * =>3. 基于ZooKeeper的分布式锁。
 * <p>
 * 为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
 * => 互斥性。在任意时刻，只有一个客户端能持有锁。
 * => 不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
 * => 具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
 * => 解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
 */
public class RedisDistributedLock {

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";


    /**
     * 尝试获取分布式锁
     *
     * @param jedis      redis java客户端
     * @param lockKey    锁的key
     * @param requestId  标识锁的唯一性
     * @param expireTime 过期时间
     * @return
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        return LOCK_SUCCESS.equals(result);

    }

    /**
     * 释放分布式锁
     * @param jedis redis java客户端
     * @param lockKey   锁的key
     * @param requestId 锁唯一标识
     * @return
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        //在eval命令执行Lua代码的时候，Lua代码将被当成一个命令去执行，并且直到eval命令执行完成，Redis才会执行其他命令
        Object result = jedis.eval(luaScript, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        return LOCK_SUCCESS.equals(requestId);

    }

}
