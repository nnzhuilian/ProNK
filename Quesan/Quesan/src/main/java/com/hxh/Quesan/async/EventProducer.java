package com.hxh.Quesan.async;

import com.alibaba.fastjson.JSONObject;
import com.hxh.Quesan.util.JedisAdapter;
import com.hxh.Quesan.util.RedisKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;
    private static final Logger logger=LoggerFactory.getLogger(EventProducer.class);
    public boolean fireEvent(EventModel eventModel){

        try{
            String key=RedisKey.getEventqueueKey();
            String json=JSONObject.toJSONString(eventModel);//一定要set和get齐全
            logger.info(key+"点赞生产"+json);
            jedisAdapter.lpush(key,json);
            return true;
        }catch (Exception e){
            //logger.error("加入事件失败");
            return false;
        }
    }
}
