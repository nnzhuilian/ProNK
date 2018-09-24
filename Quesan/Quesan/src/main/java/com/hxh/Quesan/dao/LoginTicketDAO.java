package com.hxh.Quesan.dao;

import com.hxh.Quesan.model.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketDAO {
    @Insert({"insert into login_ticket (user_id,ticket,expired,status) values (#{userId},#{ticket},#{expired},#{status})"})
    int addTicket(LoginTicket loginTicket);
    @Select({"select id,user_id,ticket,expired,status from login_ticket where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);
    @Update({"update login_ticket set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket,@Param("status") int status);
}
