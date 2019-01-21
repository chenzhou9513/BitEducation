package com.bitedu.service.redis;

import com.bitedu.common.RedisUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class RedisLock implements Lock  {


    /*

        1. 循环trylock,每次失败就sleep一段时间
        2. trylock用setnx，key是serviceId, value是时间戳
                不成功返回false
                成功返回 将value传入ThreaLoacal，设置redis key超时时间，返回true
        3. unloack 获取ThreadLocal中的value，执行lua脚本(key,value)解锁，消除ThreadLocal中对应的value
    */


    @Autowired
    private RedisUtil redisUtil;

    private  ThreadLocal<String> threadContext = new ThreadLocal<String>();

    private  long  EXPIRES_TIME = 100;



    private static final int TIME_LIMIT = 1000 * 10;

    private static final int SLEEP_TIME = 10;



    @Override
    public void lock() {

        try {
            while (!tryLock()) {
                Thread.sleep(SLEEP_TIME);
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(EXPIRES_TIME, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
    }


    public void lock(String key){

        try {
            while (!tryLock(key)) {

                Thread.sleep(SLEEP_TIME);
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public boolean tryLock(String key) throws InterruptedException {

        String value = String.valueOf(System.currentTimeMillis());
        if(redisUtil.setnx(key,value)==1){
            redisUtil.pexpire(key, EXPIRES_TIME);
            this.threadContext.set(value);
            return true;
        }
            return false;
    }

    public void unlock(String key){

        redisUtil.delWithScript(key, this.threadContext.get());
        this.threadContext.remove();
    }


    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }



}
