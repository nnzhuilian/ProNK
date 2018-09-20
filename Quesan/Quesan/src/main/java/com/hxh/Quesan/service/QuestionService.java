package com.hxh.Quesan.service;

import com.hxh.Quesan.dao.QuestionDAO;
import com.hxh.Quesan.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    public List<Question> getLatestQuestion(int userId,int offset,int limit) {
        return questionDAO.selectLatestQuestions(userId,offset,limit);
    }
}
