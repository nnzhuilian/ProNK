package com.hxh.Quesan.controller;

import com.hxh.Quesan.model.EntityType;
import com.hxh.Quesan.model.Question;
import com.hxh.Quesan.model.ViewObject;
import com.hxh.Quesan.service.FollowService;
import com.hxh.Quesan.service.QuestionService;
import com.hxh.Quesan.service.SearchService;
import com.hxh.Quesan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private static final Logger logger=LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @RequestMapping(path={"/search"},method = {RequestMethod.GET})
    public String addComment(Model model, @RequestParam("q") String keyword,
                             @RequestParam(value = "offset", defaultValue = "0") int offset,
                             @RequestParam(value = "count", defaultValue = "10") int count){
        try{
            String Key="question_title:"+keyword+" OR question_content:"+keyword;
            logger.info(Key);
            List<Question> questionList=searchService.searchQuestion(Key,offset,count,"<em>","</em>");
            List<ViewObject> vos=new ArrayList<>();
            for(Question question:questionList){
                Question q=questionService.getQuestion(question.getId());
                ViewObject vo=new ViewObject();
                if(question.getContent()!=null){
                    q.setContent(question.getContent());
                }
                if(question.getTitle()!=null){
                    q.setTitle(question.getTitle());
                }
                vo.set("question",q);
                vo.set("followCount",followService.getFollowerCount(EntityType.Comment_to_Question,question.getId()));
                vo.set("user",userService.getUser(q.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos",vos);
        }catch (Exception e){
            logger.error("搜索失败"+e.getMessage());
        }
        return "result";
    }
}
