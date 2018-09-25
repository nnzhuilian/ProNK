package com.hxh.Quesan.interceptor;


import com.hxh.Quesan.dao.LoginTicketDAO;
import com.hxh.Quesan.dao.UserDAO;
import com.hxh.Quesan.model.HostHolder;
import com.hxh.Quesan.model.LoginTicket;
import com.hxh.Quesan.model.User;
import com.hxh.Quesan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTicketDAO loginTicketDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket=null;
        for(Cookie cookie:httpServletRequest.getCookies()){
            if(cookie.getName()=="ticket"){
                ticket=cookie.getValue();
                break;
            }
        }
        if(ticket!=null){
            LoginTicket loginTicket=loginTicketDAO.selectByTicket(ticket);
            if((loginTicket==null)||(loginTicket.getExpired().before(new Date()))||(loginTicket.getStatus()!=0)){
                return true;
            }
            else {
                User user=userDAO.selectById(loginTicket.getUserId());
                hostHolder.setUsers(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null||hostHolder.getUser()!=null){
            modelAndView.addObject("user",hostHolder.getUser());////////可在前端用,相当于Model
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
