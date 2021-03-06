package com.hxh.Quesan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger=LoggerFactory.getLogger(JedisAdapter.class);
private JedisPool jedisPool;
   /* public static void print(int index, Object obj) {
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }

    public static void main(String[] argv) {
        Jedis jedis = new Jedis("redis://localhost:6379/1");
        jedis.flushDB();

        // get set
        jedis.set("hello", "world");
        print(1, jedis.get("hello"));
        jedis.rename("hello", "newhello");
        print(1, jedis.get("newhello"));
        jedis.setex("hello2", 1800, "world");

        //
        jedis.set("pv", "100");
        jedis.incr("pv");
        jedis.incrBy("pv", 5);
        print(2, jedis.get("pv"));
        jedis.decrBy("pv", 2);
        print(2, jedis.get("pv"));

        print(3, jedis.keys("*"));}*/

@Override
    public void afterPropertiesSet() throws Exception {
        jedisPool=new JedisPool("redis://localhost:6379/1");
    }
    public long sadd(String key,String value){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("点赞失败："+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return 0;
    }
    public long srem(String key,String value){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("点赞失败："+e.getMessage());
        }finally {
            if(jedis!=null) {
                jedis.close();
            }
        }
        return 0;
    }
    public long scard(String key){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("查询失败:"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return  0;
    }
    public boolean sismember(String key,String value){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("查询失败:"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return false;
    }
    public List<String> brpop(int timeout,String key){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.brpop(timeout,key);
        }catch (Exception e){
            logger.error("查询失败:"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key,String value){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.lpush(key,value);
        }catch (Exception e){
            logger.error("查询失败:"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }

    public  List<String> lrange(String key,int start,int end){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return  jedis.lrange(key,start,end);
        }catch(Exception e){
            logger.error("查询失败："+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
    public Jedis getJedis(){
        try {
            return jedisPool.getResource();
        }catch (Exception e){
            return null;
        }
    }

    public Transaction multi(Jedis jedis){
        try{
            return jedis.multi();
        }catch (Exception e){
            logger.error("事務開啓異常！");
        }finally {

        }
        return null;
    }

    public  List<Object> exec(Transaction tx,Jedis jedis){
    try{
        return tx.exec();
    }catch (Exception e){
        logger.error("添加事务exec异常："+e.getMessage());
    }finally {
        if(tx!=null){
            try{
                tx.close();
            }catch (IOException e){
                logger.error("发生异常："+e.getMessage());
            }
        }
        if(jedis!=null){
            jedis.close();
        }
    }
    return null;
    }

    public Set<String> revzrange(String key, int start, int end){//集合反向排序
    Jedis jedis=null;
    try{
        jedis=jedisPool.getResource();
        return jedis.zrevrange(key,start,end);
    }catch (Exception e){
        logger.error("发生异常"+e.getMessage());
    }finally {
        if(jedis!=null){
            jedis.close();
        }
    }
    return null;
    }

    public long zcard(String key){//集合中有多少数字
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.zcard(key);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return 0;
    }
    public Double zscore(String key,String member){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.zscore(key,member);
        }catch (Exception e){
            logger.error("发生异常："+e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }

}
