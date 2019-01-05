package com.hxh.Quesan.service;

import com.hxh.Quesan.util.JedisAdapter;
import com.hxh.Quesan.util.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;
    public boolean follow(int entityType,int entityId,int userId){
        Date date=new Date();
        String followerKey=RedisKey.getFollowerKey(entityType,entityId);
        String followeeKey=RedisKey.getFolloweeKey(userId,entityType);
        Jedis jedis=jedisAdapter.getJedis();
        Transaction tx=jedisAdapter.multi(jedis);
        tx.zadd(followerKey,date.getTime(),String.valueOf(userId));
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));
        List<Object> ret=jedisAdapter.exec(tx,jedis);
        return (ret.size()==2)&&((Long)ret.get(0)>0)&&((Long)ret.get(1)>0);
    }
    public boolean unfollow(int entityType,int entityId,int userId){
        String followerKey=RedisKey.getFollowerKey(entityType,entityId);
        String followeeKey=RedisKey.getFolloweeKey(userId,entityType);
        Jedis jedis=jedisAdapter.getJedis();
        Transaction tx=jedisAdapter.multi(jedis);
        tx.zrem(followerKey,String.valueOf(userId));
        tx.zrem(followeeKey,String.valueOf(entityId));
        List<Object> ret=jedisAdapter.exec(tx,jedis);
        return (ret.size()==2)&&((Long)ret.get(0)>0)&&((Long)ret.get(1)>0);
    }
    public List<Integer> getFollowers(int entityType,int entityId,int count){
        String followerKey=RedisKey.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapter.revzrange(followerKey,0,count));
    }
    public List<Integer> getFollowers(int entityType,int entityId,int offset,int count){
        String followerKey=RedisKey.getFollowerKey(entityType,entityId);
        return getIdsFromSet(jedisAdapter.revzrange(followerKey,offset,offset+count));
    }
    public List<Integer> getFollowees(int userId,int entityType,int count){
        String followeeKey=RedisKey.getFolloweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapter.revzrange(followeeKey,0,count));
    }
    public List<Integer> getFollowees(int userId,int entityType,int offset,int count){
        String followeeKey=RedisKey.getFolloweeKey(userId,entityType);
        return getIdsFromSet(jedisAdapter.revzrange(followeeKey,offset,offset+count));
    }
    public long getFollowerCount(int entityType,int entityId){
        String followerKey=RedisKey.getFollowerKey(entityType,entityId);
        return jedisAdapter.zcard(followerKey);
    }
    public long getFolloweeCount(int userId,int entityType){
        String followeeKey=RedisKey.getFolloweeKey(userId,entityType);
        return  jedisAdapter.zcard(followeeKey);
    }

    public List<Integer> getIdsFromSet(Set<String> idset){
        List<Integer> ids= new ArrayList<Integer>();
        for(String id:idset){
            ids.add(Integer.parseInt(id));
        }
        return ids;
    }
    public boolean isFollower(int userId,int entityType,int entityId){//判断score是否等于0，score是根据时间来的，如有会赋值
        String followerKey=RedisKey.getFollowerKey(entityType,entityId);
        return jedisAdapter.zscore(followerKey,String.valueOf(userId))!=null;
    }

}
