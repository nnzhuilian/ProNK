package com.hxh.Quesan.dao;

import com.hxh.Quesan.model.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserDAO {
    @Insert({"insert into user (name,password,salt,headUrl) values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);
    @Select({})
    User selectById(int id);
    @Update({})

    @Delete({})

}
