package com.hxh.Quesan.dao;

import com.hxh.Quesan.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentDAO {///////接口呀
    @Insert({"insert into comment (content,user_id,entity_id,entity_type,created_date,status) values (#{content},#{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
    int addComment(Comment comment);
    @Select({"select id,content,user_id,entity_id,entity_type,created_date,status from comment where entity_id=#{entityId} and entity_Type=#{entityType} order by created_Date desc"})
    List<Comment> selectcommentByentity(@Param("entityId") int entityId,@Param("entityType") int entityType);//通过实体类型和实体id查找
    @Select({"select count(id) from comment where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentcount(@Param("entityId") int entityId,@Param("entityType") int entityType);
    @Update({"update status=#{status} where id=#{id}"})
    int updateStatus(@Param("commentId") int commentId,@Param("status") int status);
}
