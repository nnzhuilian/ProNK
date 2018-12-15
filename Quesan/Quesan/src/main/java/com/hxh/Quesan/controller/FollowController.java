package com.hxh.Quesan.controller;


import com.hxh.Quesan.async.EventModel;
import com.hxh.Quesan.async.EventProducer;
import com.hxh.Quesan.async.EventType;
import com.hxh.Quesan.model.*;
import com.hxh.Quesan.service.FollowService;
import com.hxh.Quesan.service.QuestionService;
import com.hxh.Quesan.service.UserService;
import com.hxh.Quesan.util.Jsonpro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {
    private static final Logger logger=LoggerFactory.getLogger(FollowController.class);
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    QuestionService questionService;

    @RequestMapping(path={"/followUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId){
        if(hostHolder.getUser()==null){
            return Jsonpro.getJsonString(999);
        }
        boolean ret=followService.follow(EntityType.USER,userId,hostHolder.getUser().getId());
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.USER).setEntityOwnerId(userId));
        return Jsonpro.getJsonString(ret?0:1,String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.USER)));
    }

    @RequestMapping(path={"/unfollowUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId){
        if(hostHolder.getUser()==null){
            return Jsonpro.getJsonString(999);
        }
        boolean ret=followService.unfollow(EntityType.USER,userId,hostHolder.getUser().getId());
        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW).setActorId(hostHolder.getUser().getId())
        .setEntityType(EntityType.USER).setEntityOwnerId(userId));
        return Jsonpro.getJsonString(ret?0:1,String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.USER)));
    }

    @RequestMapping(path={"/followQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId){
        if(hostHolder.getUser()==null){
            return Jsonpro.getJsonString(999);
        }
        Question q=questionService.getQuestion(questionId);
        if(q==null){
            return Jsonpro.getJsonString(1,"问题不存在！");
        }

        boolean ret=followService.follow(EntityType.Comment_to_Question,questionId,hostHolder.getUser().getId());
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.Comment_to_Question).setEntityOwnerId(q.getUserId()));

        Map<String,Object> info=new HashMap<String, Object>();
        info.put("headUrl",hostHolder.getUser().getHeadUrl());
        info.put("name",hostHolder.getUser().getName());
        info.put("id",hostHolder.getUser().getId());
        info.put("count",followService.getFollowerCount(EntityType.Comment_to_Question,questionId));

        return Jsonpro.getJsonString(ret?0:1,info);
    }

    @RequestMapping(path={"/unfollowQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId) {
        if (hostHolder.getUser() == null) {
            return Jsonpro.getJsonString(999);
        }
        Question q = questionService.getQuestion(questionId);
        if (q == null) {
            return Jsonpro.getJsonString(1, "问题不存在！");
        }

        boolean ret = followService.unfollow(EntityType.Comment_to_Question, questionId, hostHolder.getUser().getId());
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW).setActorId(hostHolder.getUser().getId())
                .setEntityType(EntityType.Comment_to_Question).setEntityOwnerId(q.getUserId()));

        Map<String, Object> info = new HashMap<String, Object>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(EntityType.Comment_to_Question, questionId));

        return Jsonpro.getJsonString(ret ? 0 : 1, info);
    }

    @RequestMapping(path={"/user/{uid}/followees"},method={RequestMethod.GET})
    public  String followees(Model model,@PathVariable("uid") int userId){
        List<Integer> followeeIds=followService.getFollowees(userId,EntityType.USER,0,10);
        if(hostHolder.getUser()!=null){
            model.addAttribute("followees",getUsersInfo(hostHolder.getUser().getId(),followeeIds));
        }else{
            model.addAttribute("followees",getUsersInfo(0,followeeIds));
        }
        return "followees";
    }

    @RequestMapping(path={"/user/{uid}/followers"},method={RequestMethod.GET})
    public  String followers(Model model,@PathVariable("uid") int userId){
        List<Integer> followerIds=followService.getFollowers(EntityType.USER,userId,0,10);
        if(hostHolder.getUser()!=null){
            model.addAttribute("followers",getUsersInfo(hostHolder.getUser().getId(),followerIds));
        }else{
            model.addAttribute("followers",getUsersInfo(0,followerIds));
        }
        return "followers";
    }

    private List<ViewObject> getUsersInfo(int localUserId,List<Integer> userIds){
        List<ViewObject> userInfos=new ArrayList<>();
        for(Integer uid:userIds){
            User user=userService.getUser(uid);
            if(user==null){
                continue;
            }

            ViewObject vo=new ViewObject();
            vo.set("user",user);
            vo.set("followerCount",followService.getFollowerCount(EntityType.USER,uid));
            vo.set("followeeCount",followService.getFolloweeCount(uid,EntityType.USER));
            if(localUserId!=0){
                vo.set("followed",followService.isFollower(localUserId,EntityType.USER,uid));
            }else{
                vo.set("followed",false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }
}
