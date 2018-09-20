package com.hxh.Quesan.controller;

import com.hxh.Quesan.model.User;
import com.hxh.Quesan.service.IOCtest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller//加一个注解，注明这是一个controller
public class IndexController {
    @Autowired
    IOCtest ioc;
    //@RequestMapping("/")//访问网页的地址(/)，就返回下面函数的字符串
    @RequestMapping(path={"/","/index"})//‘/’和‘/index'都能访问
    @ResponseBody //访问的是字符串而不是模板
    public String index(HttpSession httpSession){
        return "Hello!"+httpSession.getAttribute("msg")+ioc.getMessage(1);//IOC注入示例及跳转示例
    }//从之前的跳转传递过来消息

    @RequestMapping(path={"/profile/{groupid}/{userid}"},method = {RequestMethod.GET})//只允许post(一般用于递交数据），.GET(一般用于获取数据）
    @ResponseBody
    public String profile(@PathVariable("userid") int userid, @PathVariable("groupid") String groupid,
                          //[路径]里的参数通过PathVariable解析到变量
                          @RequestParam(value="type") int type,//[请求]里的参数解析。
                          @RequestParam(value="key",defaultValue = "zz",required = false) String key){
        //防止不提供，给defaultValue默认参数（require：true必须提供，false不一定提供，不提供为null）
        return String.format("Profile id of %s / %d, t:%d, k: %s",groupid, userid,type,key);
    }
    @RequestMapping(path={"/vm"},method = {RequestMethod.GET})
    public String template(Model model){//通过model传递数据到模板
        model.addAttribute("value1","hahahahaha!");//将value传递到页面
        List<String> colors=Arrays.asList(new String[]{"red","blue","green"});
        model.addAttribute("colors",colors);
        Map<Integer,String> map=new HashMap<Integer,String>();
        map.put(1,"a");
        map.put(2,"b");
        model.addAttribute("map",map);
        model.addAttribute("user",new User("hxh",3));
        return "home";
    }
    @RequestMapping(path={"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession httpsession,
                          @CookieValue("JSESSIONID") String sessionid){//可通过注解方式读取cookie中的值/注意传入为字符串，
        StringBuilder sb=new StringBuilder();
        Enumeration<String> headnames=request.getHeaderNames();
        while(headnames.hasMoreElements()){
            String name=headnames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        if(request.getCookies()!=null){
            for(Cookie cookie : request.getCookies()){
                sb.append(cookie.getName()+":"+cookie.getValue());
            }
        }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURI()+"<br>");
        sb.append("Jcookieid:"+sessionid);
        response.addCookie(new Cookie("zyl01","zyl weilaikeqi"));
        response.addHeader("zyl02","sw A");
        return sb.toString();
    }
    @RequestMapping(path={"/redirect/{code}"},method={RequestMethod.GET})//重定向 301临时跳转302跳转
    public String redirect(@PathVariable("code") int code,HttpSession httpsession){
        httpsession.setAttribute("msg","zyl48~!");
        return "redirect:/";//输入/redirect/301跳转到/
    }

    @RequestMapping(path={"/redirectq/{code}"},method={RequestMethod.GET})//重定向 301临时跳转302跳转
    public RedirectView redirectq(@PathVariable("code") int code, HttpSession httpsession){
        httpsession.setAttribute("msg","zyl48~!");
       RedirectView rv=new RedirectView("/",true);
        if(code==301){
            rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);//临时跳转
       }
       return rv;
    }
    /////////////////////////////error处理/////////////////////////////
    @RequestMapping(path={"admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String errortest(@RequestParam("key") String key) {//注解后参数都为括号
        if("admin".equals(key)) {//字符串比较用equals
            return key;
        }
        throw new IllegalArgumentException("wrong");//将错误抛出
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return e.getMessage();//接收抛出的错误
    }

}
