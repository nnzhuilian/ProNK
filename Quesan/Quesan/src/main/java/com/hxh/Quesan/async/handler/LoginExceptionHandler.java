package com.hxh.Quesan.async.handler;

import com.hxh.Quesan.async.EventHandler;
import com.hxh.Quesan.async.EventModel;
import com.hxh.Quesan.async.EventType;
import com.hxh.Quesan.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    MailSender mailSender;
    @Override
    public void doHandle(EventModel event) {
        //判断发现这个用户登陆异常
        Map<String,Object> map= new HashMap<String, Object>();
        map.put("username",event.getExt("username"));
        mailSender.sendWithHTMLTemplate(event.getExt("email"),"登陆IP异常","mails/login_exception.html",map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
