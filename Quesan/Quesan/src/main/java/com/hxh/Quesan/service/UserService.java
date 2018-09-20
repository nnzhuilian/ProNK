package com.hxh.Quesan.service;

import com.hxh.Quesan.dao.UserDAO;
import com.hxh.Quesan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    public User getUser(int id){
        return userDAO.selectById(id);
    }

}
