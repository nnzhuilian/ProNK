package com.hxh.Quesan.async.handler;

import com.hxh.Quesan.async.EventHandler;
import com.hxh.Quesan.async.EventModel;
import com.hxh.Quesan.async.EventType;
import com.hxh.Quesan.model.Message;
import com.hxh.Quesan.model.User;
import com.hxh.Quesan.service.QuestionService;
import com.hxh.Quesan.service.UserService;
import com.hxh.Quesan.util.Jsonpro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    UserService userService;
    @Override
    public void doHandle(EventModel event) {
        Message message=new Message();
        message.setFromId(Jsonpro.ADMIN_UERID);
        message.setCreatedDate(new Date());
        message.setHasRead(0);
        message.setToId(event.getEntityOwnerId());
        message.setConversationId(message.editConversationId(Jsonpro.ADMIN_UERID,event.getEntityOwnerId()));
        User user=userService.getUser(event.getActorId());
        message.setContent("用户:"+user.getName()+"赞了你http://127.0.0.1:8080/question/"+event.getEntityId()+"的回答");
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
