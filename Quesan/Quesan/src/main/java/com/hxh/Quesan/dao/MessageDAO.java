package com.hxh.Quesan.dao;

import com.hxh.Quesan.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageDAO {
    String ALL=" id,from_id,to_id,content,conversation_id,created_date,has_read ";
    String ADD=" from_id,to_id,content,conversation_id,created_date,has_read ";
    @Insert({"insert into message ( "+ADD+" ) values (#{fromId},#{toId},#{content},#{conversationId},#{createdDate},#{hasRead})"})
    int addMessage(Message message);
    @Select({"select "+ALL+" from message where conversation_id=#{conversationId} order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,@Param("offset") int offset,@Param("limit") int limit);
    //传出id,数据库中不会改变
    @Select({"select "+ALL+" , count(id) as id from ( select * from message where from_id=#{userId} or to_id=#{userId} order by created_date desc ) tt group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);
    @Select({"select count(id) from message where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId,@Param("conversationId") String conversationId);
    /*@Update({"update message set has_read=1 where to_id=#{toId}"})
    void setMessageReadState(int toId);*/
}
