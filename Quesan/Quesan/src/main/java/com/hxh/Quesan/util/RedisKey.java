package com.hxh.Quesan.util;

public class RedisKey {
    private static final String SPLIT=":";
    private static final String BIZ_LIKE="LIKE";
    private static final String BIZ_DISLIKE="DISLIKE";
    private static final String BIZ_EVENTQUEUE="EVENTQUEUE";
    private static final String BIZ_FOLLOWER="FOLLOWER";
    private static final String BIZ_FOLLOWEE="FOLLOWEE";

    public static String getLikeKey(int entityType,int entityName){
        return BIZ_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityName);
    }

    public static String getDISLikeKey(int entityType,int entityName){
        return BIZ_DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityName);
    }

    public static String getEventqueueKey(){
        return BIZ_EVENTQUEUE;
    }

    public static String getFollowerKey(int entityType,int entityId){//每个实体所有粉丝的Key
        return BIZ_FOLLOWER+String.valueOf(entityType)+String.valueOf(entityId);
    }
    public static String getFolloweeKey(int userId,int entityType){//某一个用户关注的某一类实体的key
        return BIZ_FOLLOWEE+String.valueOf(userId)+String.valueOf(entityType);
    }

}
