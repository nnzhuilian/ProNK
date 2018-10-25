package com.hxh.Quesan.service;

import com.hxh.Quesan.dao.MessageDAO;
import com.hxh.Quesan.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;
    @Autowired
    SensivtiveService sensivtiveService;
    public int addMessage(Message message){
        message.setContent(sensivtiveService.filter(message.getContent()));
        return messageDAO.addMessage(message)>0?message.getId():0;
    }
    public List<Message> getConversationDetail(String conversationId,int offset,int limit){
        return messageDAO.getConversationDetail(conversationId,offset,limit);
    }
    public List<Message> getConversationList(int userId,int offset,int limit){
        return messageDAO.getConversationList(userId,offset,limit);
    }
    public int getConversationUnreadCount(int userId,String conversationId){
        return messageDAO.getConversationUnreadCount(userId,conversationId);
    }
    /*public void setMessageReadState(int toId){
        messageDAO.setMessageReadState(toId);
    }*/
}
