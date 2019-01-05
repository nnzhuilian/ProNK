package com.hxh.Quesan.controller;

import com.hxh.Quesan.async.EventModel;
import com.hxh.Quesan.async.EventProducer;
import com.hxh.Quesan.async.EventType;
import com.hxh.Quesan.model.Comment;
import com.hxh.Quesan.model.EntityType;
import com.hxh.Quesan.model.HostHolder;
import com.hxh.Quesan.service.CommentService;
import com.hxh.Quesan.service.QuestionService;
import com.hxh.Quesan.util.Jsonpro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    private  static final Logger logger=LoggerFactory.getLogger(CommentController.class);
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuestionService questionService;
    @Autowired
    EventProducer eventProducer;
    @RequestMapping(path={"/addComment"},method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,@RequestParam("content") String content){
        try{
        Comment comment=new Comment();
        comment.setContent(content);
        if(hostHolder.getUser()!=null){
            comment.setUserId(hostHolder.getUser().getId());
        }else{
            comment.setUserId(Jsonpro.ANONYMOUS_UERID);
        }
        comment.setStatus(0);
        comment.setEntityType(EntityType.Comment_to_Question);
        comment.setEntityId(questionId);
        comment.setCreatedDate(new Date());
        commentService.addComment(comment);
        questionService.setCommentcount(commentService.getCommentCount(questionId,EntityType.Comment_to_Question),questionId);
        eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId())
                    .setEntityId(questionId));

        }catch (Exception e){
            logger.error("添加异常"+e.getMessage());
        }
        return "redirect:/question/"+questionId;
    }
}
