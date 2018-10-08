package com.hxh.Quesan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController {
    private static final Logger logger=LoggerFactory.getLogger(QuestionController.class);
    @RequestMapping(value = {"question/add"},method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content){
        try{

        }catch (Exception e){
            logger.error("增加项目失败"+e.getMessage());
        }
    }
}
