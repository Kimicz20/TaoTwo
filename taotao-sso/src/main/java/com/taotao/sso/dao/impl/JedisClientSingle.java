package com.taotao.sso.dao.impl;

import com.taotao.sso.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 单机版 Jedis类
 * Created by geek on 2017/6/30.
 */
public class JedisClientSingle implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get(key);
        jedis.close();
        return string;

    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.set(key,value);
        jedis.close();
        return string;
    }

    @Override
    public long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long string = jedis.del( key);
        jedis.close();
        return string;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.hget(hkey,key);
        jedis.close();
        return string;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        long string = jedis.hset(hkey,key,value);
        jedis.close();
        return string;
    }

    @Override
    public long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long string = jedis.incr(key);
        jedis.close();
        return string;
    }

    @Override
    public long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long string = jedis.expire(key,second);
        jedis.close();
        return string;
    }

    @Override
    public long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long string = jedis.ttl(key);
        jedis.close();
        return string;
    }

    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        Long string = jedis.hdel(hkey,key);
        jedis.close();
        return string;
    }
}
