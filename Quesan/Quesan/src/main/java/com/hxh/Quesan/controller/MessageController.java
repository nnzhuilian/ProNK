package com.hxh.Quesan.controller;

import com.hxh.Quesan.model.HostHolder;
import com.hxh.Quesan.model.Message;
import com.hxh.Quesan.model.User;
import com.hxh.Quesan.model.ViewObject;
import com.hxh.Quesan.service.MessageService;
import com.hxh.Quesan.service.UserService;
import com.hxh.Quesan.util.Jsonpro;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class MessageController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    private static final Logger logger=LoggerFactory.getLogger(MessageController.class);
    @RequestMapping(path = {"/msg/addMessage"},method = RequestMethod.POST)
    @ResponseBody
    public String addMessage(@RequestParam("toName")String userName,@RequestParam("content")String content){
        try{
            if(hostHolder.getUser()==null){
                return Jsonpro.getJsonString(999,"未登录");
            }
            User user=userService.selectByName(userName);
            if(user==null){
                return Jsonpro.getJsonString(1,"该用户不存在");
            }
            Message message=new Message();
            message.setContent(content);
            message.setToId(user.getId());
            message.setFromId(hostHolder.getUser().getId());
            //message.setConversationId(message.editConversationId(hostHolder.getUser().getId(),user.getId()));
            message.setCreatedDate(new Date());
            message.setHasRead(0);
            messageService.addMessage(message);
            return Jsonpro.getJsonString(0);
        }catch (Exception e){
            logger.error("发送失败"+e.getMessage());
            return Jsonpro.getJsonString(1,"发送失败");
        }
    }
    @RequestMapping(path = {"/msg/list"},method = RequestMethod.GET)
    public String getConversationList(Model model){
        if(hostHolder.getUser()==null){
            return "redirect:/reglogin";
        }
        try {
            List<Message> conversations=messageService.getConversationList(hostHolder.getUser().getId(),0,10);
            List<ViewObject> VOS=new ArrayList<>();
            for(Message conversation:conversations){
                ViewObject vo=new ViewObject();
                vo.set("conversation",conversation);
                int target=conversation.getFromId()==hostHolder.getUser().getId()?conversation.getToId():conversation.getFromId();
                vo.set("user",userService.getUser(target));
                vo.set("unread",messageService.getConversationUnreadCount(hostHolder.getUser().getId(),conversation.getConversationId()));
                VOS.add(vo);
            }
            model.addAttribute("conversations",VOS);
        }catch (Exception e){
            logger.error("获取列表失败："+e.getMessage());
        }
        return "letter";
    }
    @RequestMapping(path = {"msg/detail"},method = RequestMethod.GET)
    public String getConversationDetail(Model model, @Param("conversationId") String conversationId){
        try{
            messageService.setMessageReadState(hostHolder.getUser().getId());
            List<Message> messages=messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject> VOS=new ArrayList<>();
            for(Message message:messages){
                ViewObject vo=new ViewObject();
                vo.set("message",message);
                vo.set("user",userService.getUser(message.getFromId()));
                VOS.add(vo);
            }
            model.addAttribute("messages",VOS);
        }catch (Exception e){
            logger.error("读取失败:"+e.getMessage());
        }
        return "letterDetail";
    }
}
