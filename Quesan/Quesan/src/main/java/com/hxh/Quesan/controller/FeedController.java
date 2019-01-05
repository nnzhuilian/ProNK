package com.hxh.Quesan.controller;

import com.hxh.Quesan.model.EntityType;
import com.hxh.Quesan.model.Feed;
import com.hxh.Quesan.model.HostHolder;
import com.hxh.Quesan.service.FeedService;
import com.hxh.Quesan.service.FollowService;
import com.hxh.Quesan.util.JedisAdapter;
import com.hxh.Quesan.util.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    @Autowired
    FeedService feedService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FollowService followService;
    @Autowired
    JedisAdapter jedisAdapter;
    @RequestMapping(path={"/pullfeeds"},method = {RequestMethod.GET})
    private String getPullFeeds(Model model){
        int localUserId = hostHolder.getUser()==null?0:hostHolder.getUser().getId();

        List<Integer> followees=new ArrayList<Integer>();
        if(localUserId!=0){
            followees=followService.getFollowees(localUserId,EntityType.USER,Integer.MAX_VALUE);

        }
        List<Feed> feeds=feedService.getUsersFeeds(followees,Integer.MAX_VALUE,10);
        model.addAttribute("feeds",feeds);
        return "feeds";
    }

    @RequestMapping(path={"/pushfeeds"},method = {RequestMethod.GET})
    private String getPushFeeds(Model model){
        int localUserId = hostHolder.getUser()==null?0:hostHolder.getUser().getId();
        List<String> feedIds=jedisAdapter.lrange(RedisKey.getTimelineKey(localUserId),0,10);
        List<Feed> feeds=new ArrayList<Feed>();
        for(String feedId:feedIds){
            Feed feed=feedService.getFeedById(Integer.parseInt(feedId));
            if(feed==null){
                continue;
            }
            feeds.add(feed);
        }
        model.addAttribute("feeds",feeds);
        return "feeds";
    }
}
