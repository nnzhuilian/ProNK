package com.hxh.Quesan.async.handler;

import com.hxh.Quesan.async.EventHandler;
import com.hxh.Quesan.async.EventModel;
import com.hxh.Quesan.async.EventType;
import com.hxh.Quesan.model.EntityType;
import com.hxh.Quesan.model.Feed;
import com.hxh.Quesan.model.Question;
import com.hxh.Quesan.model.User;
import com.hxh.Quesan.service.FeedService;
import com.hxh.Quesan.service.QuestionService;
import com.hxh.Quesan.service.UserService;
import com.hxh.Quesan.util.Jsonpro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class FeedHandler implements EventHandler {
    private static final Logger logger=LoggerFactory.getLogger(FeedHandler.class);
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FeedService feedService;
    private String feedDataBuilder(EventModel event){

        Map<String,String> map=new HashMap<String,String>();
        //触发用户是通用的
        User actor=userService.getUser(event.getActorId());
        if(actor==null) {
            return null;
        }
        map.put("userId",String.valueOf(actor.getId()));
        map.put("headUrl",actor.getHeadUrl());
        map.put("userName",actor.getName());
        if(event.getEventType()==EventType.COMMENT||(event.getEventType()==EventType.FOLLOW&&event.getEntityType()==EntityType.Comment_to_Question)){
            Question question=questionService.getQuestion(event.getEntityOwnerId());
            if(question==null){
                logger.info("执行do");return null;
            }

            map.put("questionId",String.valueOf(question.getId()));
            map.put("questionTitle",question.getTitle());
            return Jsonpro.getJsonString(map);
        }
        return null;
    }
    @Override
    public void doHandle(EventModel event) {
        Feed feed=new Feed();
        feed.setUserId(event.getActorId());
        feed.setCreatedDate(new Date());
        feed.setType(event.getEventType().getValue());
        feed.setData(feedDataBuilder(event));
        if(feed.getData()==null){
            return;
        }

        feedService.addFeed(feed);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.FOLLOW,EventType.COMMENT});
    }
}
