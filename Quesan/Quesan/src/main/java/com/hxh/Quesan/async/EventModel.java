package com.hxh.Quesan.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {//event
    private int entityType;
    private int entityId;
    private EventType eventType;
    private int actorId;//触发者
    private int entityOwnerId;//event与某个人相关

    private Map<String,String> exts=new HashMap<String, String>();//扩展字段，保留事件发生时的各种信息，同vo。

    public EventModel(){

    }

    public EventModel(EventType eventType) {
        this.eventType = eventType;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }


    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }


    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;

        return this;
    }
    public Map<String, String> getExts() {
        return exts;
    }

    public void setExts(Map<String, String> exts) {
        this.exts = exts;
    }

    public String getExt(String key) {
        return exts.get(key);
    }

    public EventModel setExt(String key,String value) {
        exts.put(key, value);
        return this;
    }


}