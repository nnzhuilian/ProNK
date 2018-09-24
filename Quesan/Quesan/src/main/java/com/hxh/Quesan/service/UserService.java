package com.hxh.Quesan.service;

import com.hxh.Quesan.dao.UserDAO;
import com.hxh.Quesan.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.hxh.Quesan.util.MD5Ari.MD5;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public Map<String,String> register(String username,String password){
        Map<String,String> map=new HashMap<String, String>();
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
        return map;
    }
    public User getUser(int id){
        return userDAO.selectById(id);
    }


}
