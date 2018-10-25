package com.hxh.Quesan.controller;

import com.hxh.Quesan.model.*;
import com.hxh.Quesan.service.CommentService;
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
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    private static final Logger logger=LoggerFactory.getLogger(QuestionController.class);
    @RequestMapping(value = {"question/add"},method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content){
        try{
            Question question=new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if(hostHolder.getUser()==null){
                question.setUserId(Jsonpro.ANONYMOUS_UERID);//return Jsonpro.getJsonString(999);
            }else{
            question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question)>0){
                return Jsonpro.getJsonString(0);/////////////////传给前端，判断是否跳转（比如未登录就不跳转刷新
            };
        }catch (Exception e){
            logger.error("增加项目失败"+e.getMessage());
        }
        return Jsonpro.getJsonString(1,"失败");
    }
    @RequestMapping(path = {"/question/{qid}"},method = RequestMethod.GET)
    public String questionDetail(Model model,@PathVariable("qid") int qid){
        Question question=questionService.getQuestion(qid);
        model.addAttribute("question",question);
        //model.addAttribute("user",userService.getUser(question.getUserId()));
        List<Comment> comments= commentService.getComments(qid,EntityType.Comment_to_Question);
        List<ViewObject> Vos=new ArrayList<>();
        for(Comment comment:comments){
        ViewObject vo=new ViewObject();
        vo.set("comment",comment);
        vo.set("user",userService.getUser(comment.getUserId()));
        Vos.add(vo);
        }
        model.addAttribute("comments",Vos);
        return "detail";
    }

}
