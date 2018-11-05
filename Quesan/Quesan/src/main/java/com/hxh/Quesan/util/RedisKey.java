package com.hxh.Quesan.util;

public class RedisKey {
    private static final String SPLIT=":";
    private static final String BIZ_LIKE="LIKE";
    private static final String BIZ_DISLIKE="DISLIKE";
    private static final String BIZ_EVENTQUEUE="EVENTQUEUE";

    public static String getLikeKey(int entityType,int entityName){
        return BIZ_LIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityName);
    }

    public static String getDISLikeKey(int entityType,int entityName){
        return BIZ_DISLIKE+SPLIT+String.valueOf(entityType)+SPLIT+String.valueOf(entityName);
    }

    public static String getEventqueueKey(){
        return BIZ_EVENTQUEUE;
    }

}
