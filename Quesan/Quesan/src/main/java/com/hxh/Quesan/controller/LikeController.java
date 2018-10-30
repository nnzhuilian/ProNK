package com.hxh.Quesan.controller;

import com.hxh.Quesan.model.EntityType;
import com.hxh.Quesan.model.HostHolder;
import com.hxh.Quesan.service.LikeService;
import com.hxh.Quesan.util.Jsonpro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;

    @RequestMapping(path = {"/like"},method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId" )int commentId){
        if(hostHolder.getUser()==null){
            return Jsonpro.getJsonString(999);
        }
        long likecount = likeService.like(hostHolder.getUser().getId(),EntityType.Comment_to_Comment,commentId);
        return Jsonpro.getJsonString(0,String.valueOf(likecount));
    }
    @RequestMapping(path = {"/dislike"},method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId" )int commentId){
        if(hostHolder.getUser()==null){
            return Jsonpro.getJsonString(999);
        }
        long likecount = likeService.dislike(hostHolder.getUser().getId(),EntityType.Comment_to_Comment,commentId);
        return Jsonpro.getJsonString(0,String.valueOf(likecount));
    }
}
