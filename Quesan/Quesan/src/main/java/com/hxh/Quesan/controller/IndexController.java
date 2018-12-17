package com.hxh.Quesan.controller;

import com.hxh.Quesan.model.EntityType;
import com.hxh.Quesan.model.HostHolder;
import com.hxh.Quesan.model.Question;
import com.hxh.Quesan.model.ViewObject;
import com.hxh.Quesan.service.CommentService;
import com.hxh.Quesan.service.FollowService;
import com.hxh.Quesan.service.QuestionService;
import com.hxh.Quesan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
@Autowired
    UserService userService;
@Autowired
    QuestionService questionService;
@Autowired
    FollowService followService;
@Autowired
    HostHolder hostHolder;
@Autowired
    CommentService commentService;

private  List<ViewObject> getVos(int userId,int offset,int limit){
    List<Question> ques=questionService.getLatestQuestion(userId,offset,limit);
    List<ViewObject> vos=new ArrayList<ViewObject>();
    for(Question que:ques){
        ViewObject vo=new ViewObject();
        vo.set("question",que);
        vo.set("user",userService.getUser(que.getUserId()));
        vo.set("followCount",followService.getFollowerCount(EntityType.Comment_to_Question,que.getId()));
        vos.add(vo);
    }
    return  vos;
}
@RequestMapping(path={"/","/index"},method = {RequestMethod.GET})
    public String index(Model model){
    model.addAttribute("VOS",getVos(0,0,10));
    return "index";
}
@RequestMapping(path={"/user/{userId}"},method = {RequestMethod.GET,RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId){
    model.addAttribute("VOS",getVos(userId,0,10));
    ViewObject profileUser=new ViewObject();
    profileUser.set("user",userService.getUser(userId));
    if(hostHolder.getUser()!=null){
        profileUser.set("followed",followService.isFollower(hostHolder.getUser().getId(),EntityType.USER,userId));
    }
    else{
        profileUser.set("followed",false);
    }
    profileUser.set("followerCount",followService.getFollowerCount(EntityType.USER,userId));
    profileUser.set("followeeCount",followService.getFolloweeCount(userId,EntityType.USER));
    profileUser.set("commentCount",commentService.getUserCommentCount(userId));
    model.addAttribute("profileUser",profileUser);
    return "profile";
}

}
