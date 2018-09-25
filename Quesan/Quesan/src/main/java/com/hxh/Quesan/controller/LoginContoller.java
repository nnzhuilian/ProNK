package com.hxh.Quesan.controller;

import com.hxh.Quesan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginContoller {
    private static final Logger logger=LoggerFactory.getLogger(IndexController.class);
@Autowired
    UserService userService;

    @RequestMapping(path={"/register/"},method = {RequestMethod.POST})
    public String register(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse httpServletResponse){
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
            if(map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
            }
            return "redirect:/";
        }catch (Exception e){
            logger.error("注册异常:"+e.getMessage());
            return "login";
        }
    }
    @RequestMapping(path={"/login/"},method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse httpServletResponse){
        try {
            Map<String, String> map = userService.login(username, password);
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
            if(map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);
            }
            return "redirect:/";
        }catch (Exception e){
            logger.error("登陆异常:"+e.getMessage());
            return "login";
        }
    }


    @RequestMapping(path={"/reglogin"},method = {RequestMethod.GET})
    public String reg(Model model){
        return "login";
    }

}