package com.hxh.Quesan.dao;

import com.hxh.Quesan.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedDAO {
    @Insert({"insert into feed (type,user_id,created_date,data) values (#{type},#{userId},#{createdDate},#{data})"})
    int addFeed(Feed feed);
    @Select({"select id,type,user_id,created_date,data from feed where id=#{id}"})
    Feed getFeedById(int id);

    List<Feed> selectUsersFeeds(@Param("maxId") int maxId,
                              @Param("userIds") List<Integer> userIds,
                              @Param("count") int count);


}
