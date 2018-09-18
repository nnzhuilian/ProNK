package com.hxh.Quesan.controller;

import com.hxh.Quesan.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller//加一个注解，注明这是一个controller
public class IndexController {
    //@RequestMapping("/")//访问网页的地址(/)，就返回下面函数的字符串
    @RequestMapping(path={"/","/index"})//‘/’和‘/index'都能访问
    @ResponseBody //访问的是字符串而不是模板
    public String index(){
        return "Hello!";
    }

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
    public String request(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession httpsession){
        return "";
    }

}
