package com.hxh.Quesan.service;

import com.hxh.Quesan.dao.QuestionDAO;
import com.hxh.Quesan.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.HtmlEscapeTag;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    SensivtiveService sensivtiveService;

    public List<Question> getLatestQuestion(int userId,int offset,int limit) {
        return questionDAO.selectLatestQuestions(userId,offset,limit);
    }
    public Question getQuestion(int id){
        return questionDAO.selectById(id);
    }
    public int addQuestion(Question question){
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));///////////////////过滤html
        question.setContent(sensivtiveService.filter(question.getContent()));
        question.setTitle(sensivtiveService.filter(question.getTitle()));///////////////////过滤敏感词
        return questionDAO.addQuestion(question)>0?question.getId():0;
    }
}
