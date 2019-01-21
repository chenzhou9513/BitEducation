package com.bitedu.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;

@Component
public class RedisUtil {

    @Autowired
    private JedisPool jedisPool;


    private static String script = "if redis.call('get',KEYS[1]) == ARGV[1] then\n" +
            "        return redis.call('del',KEYS[1])\n" +
            "    else\n" +
            "        return 0\n" +
            "    end";

    public Jedis getJedisConnection(){
        return jedisPool.getResource();
    }


    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            return 0L;
        } finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    public void pexpire(String key, long value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.pexpire(key, value);
        } catch (Exception e) {
        } finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    public void del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
        } finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    public void delWithScript(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            jedis.eval(script, Arrays.asList(key), Arrays.asList(value));
        } catch (Exception e) {
        } finally {
            if(jedis!=null)
                jedis.close();
        }
    }



}
