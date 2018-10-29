package com.hxh.Quesan.service;

import com.hxh.Quesan.util.JedisAdapter;
import com.hxh.Quesan.util.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;

public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long like(int userId,int entityType,int entityId){
        String likekey=RedisKey.getLikeKey(entityType,entityId);
        jedisAdapter.sadd(likekey,String.valueOf(userId));

        String dislikekey=RedisKey.getDISLikeKey(entityType,entityId);
        jedisAdapter.srem(dislikekey,String.valueOf(userId));

        return jedisAdapter.scard(likekey);
    }

    public long dislike(int userId,int entityType,int entityId) {
        String dislikekey = RedisKey.getDISLikeKey(entityType, entityId);
        jedisAdapter.sadd(dislikekey, String.valueOf(userId));

        String likekey = RedisKey.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likekey, String.valueOf(userId));

        return jedisAdapter.scard(likekey);
    }

    public int getLikeState(int userId,int entityType,int entityId){
        String dislikekey = RedisKey.getDISLikeKey(entityType, entityId);
        String likekey = RedisKey.getLikeKey(entityType, entityId);
        if(jedisAdapter.sismember(likekey,String.valueOf(userId))){
            return 1;
        }else if(jedisAdapter.sismember(dislikekey,String.valueOf(userId))){
            return -1;
        }else {
            return 0;
        }
    }

    public long getCount(int entityType,int entityId){
        String likekey = RedisKey.getLikeKey(entityType, entityId);
        return jedisAdapter.scard(likekey);
    }

}
