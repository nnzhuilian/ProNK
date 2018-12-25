package com.hxh.Quesan.service;

import com.hxh.Quesan.dao.FeedDAO;
import com.hxh.Quesan.model.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;
    public boolean addFeed(Feed feed){
        return feedDAO.addFeed(feed)>0?true:false;
    }
    public Feed getFeedById(int id){
        return feedDAO.getFeedById(id);
    }
    public List<Feed> getUsersFeeds(List<Integer> userIds,int maxId,int count){
        return feedDAO.selectUsersFeeds(maxId,userIds,count);
    }
}
