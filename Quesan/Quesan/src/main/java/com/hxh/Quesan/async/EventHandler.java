package com.hxh.Quesan.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel event);//处理
    List<EventType> getSupportEventTypes();//注册，对这些event是关心的。
}
