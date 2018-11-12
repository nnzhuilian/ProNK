package com.hxh.Quesan.service;

import com.hxh.Quesan.dao.LoginTicketDAO;
import com.hxh.Quesan.dao.UserDAO;
import com.hxh.Quesan.model.LoginTicket;
import com.hxh.Quesan.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.hxh.Quesan.util.MD5Ari.MD5;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    LoginTicketDAO loginTicketDAO;

    public Map<String,Object> register(String username,String password){
        Map<String,Object> map=new HashMap<String, Object>();
        if((StringUtils.isBlank(username))||(StringUtils.isBlank(password))){
            map.put("msg","用户名或密码不能为空");
            return map;
        }

        if(userDAO.selectByName(username)!=null){
            map.put("msg","用户名已存在");
            return map;
        }
        User user=new User();
        user.setName(username);
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }
    public Map<String ,Object> login(String username,String password){
        Map<String,Object> map=new HashMap<String,Object>();
        if((StringUtils.isBlank(username))||(StringUtils.isBlank(password))){
            map.put("msg","用户名或密码不能为空");
            return map;
        }
        User user=userDAO.selectByName(username);
        if (user==null){
            map.put("msg","用户名不存在");
            return map;
        }
        else{
            if(!(user.getPassword()).equals(MD5(password+user.getSalt()))){
                map.put("msg","用户名或密码错误");
                return map;
            }
        }
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        map.put("userId",user.getId());
        return map;
    }
    public User getUser(int id){
        return userDAO.selectById(id);
    }
    public String addLoginTicket(int userId){
        LoginTicket ticket=new LoginTicket();
        ticket.setUserId(userId);
        Date now=new Date();
        now.setTime(now.getTime()+3600*24*100);
        ticket.setExpired(now);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }
    public User selectByName(String name){
        return userDAO.selectByName(name);
    }
    public void setTicketStatus(String ticket){
        loginTicketDAO.updateStatus(ticket,1);
    }


}
