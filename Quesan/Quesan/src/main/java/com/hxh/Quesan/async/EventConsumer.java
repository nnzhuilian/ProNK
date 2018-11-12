package com.hxh.Quesan.async;


import com.alibaba.fastjson.JSON;
import com.hxh.Quesan.controller.MessageController;
import com.hxh.Quesan.util.JedisAdapter;
import com.hxh.Quesan.util.RedisKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger logger=LoggerFactory.getLogger(EventConsumer.class);
    @Autowired
    JedisAdapter jedisAdapter;
    private Map<EventType,List<EventHandler>> config=new HashMap<EventType, List<EventHandler>>();
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
    @Override
    public void afterPropertiesSet() throws Exception {//根据Handler，找出所有可支持的event
        Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);//找出工程中所有实现EventHandler接口的类
        if(beans!=null){
            for(Map.Entry<String ,EventHandler> entry:beans.entrySet()){
                List<EventType> eventTypes=entry.getValue().getSupportEventTypes();
                for (EventType eventType:eventTypes){
                    if(!config.containsKey(eventType)){
                    config.put(eventType,new ArrayList<EventHandler>());//若config中没有这个事件类型，则加入
                        logger.info("加入类型"+eventType.getValue());
                    }
                    config.get(eventType).add(entry.getValue());//为事件类型关联多个事件处理器。
                }
            }
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key=RedisKey.getEventqueueKey();
                    List<String> events=jedisAdapter.brpop(0,key);////弹出两个数，一个key,一个value？

                    for(String event:events){
                        if(event.equals(key)){
                            logger.info(key);
                            continue;
                        }
                        logger.info("POP:"+event);
                        EventModel eventModel=JSON.parseObject(event,EventModel.class);///////////////wong出错
                        logger.info(""+eventModel.getEventType().getValue());
                        if(!config.containsKey(eventModel.getEventType())){
                            logger.error("无法识别事件");
                            continue;
                        }
                        for(EventHandler eventHandler:config.get(eventModel.getEventType())){//类似于路由，把事件分发给handler
                            eventHandler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }



}
