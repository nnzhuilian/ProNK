/*package com.hxh.Quesan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hxh.Quesan.async.EventModel;
import com.hxh.Quesan.model.User;

public class JSONtest {
    public static void main(String args[]) {
        User user=new User();
        user.setId(1);
        user.setName("haha");
        String json=JSONObject.toJSONString(user);
        User user2=JSON.parseObject(json,User.class);
        System.out.print(user2.getName());
        EventModel eventModel = JSON.parseObject("{\"actorId\":12,\"entityId\":2,\"entityOwnerId\":3,\"entityType\":2,\"eventType\":\"LIKE\",\"exts\":{\"questionId\":\"10\"}}", EventModel.class);
    System.out.println(eventModel.getEventType().getValue());
}
}*/
