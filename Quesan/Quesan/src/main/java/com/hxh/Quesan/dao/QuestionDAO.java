package com.hxh.Quesan.dao;

import com.hxh.Quesan.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionDAO {
    @Insert({"insert into question (id,title,content,created_date,user_id,comment_count) values (#{id},#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit
    );


}
