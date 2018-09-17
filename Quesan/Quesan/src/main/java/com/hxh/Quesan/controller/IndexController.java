package com.hxh.Quesan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String template(){
        return "home";
    }
}
