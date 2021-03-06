package com.hxh.Quesan.model;

import java.util.Date;

public class Message {
    private int Id;
    private int fromId;
    private int toId;
    private String content;
    private String conversationId;
    private Date createdDate;
    private int hasRead;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        if(fromId<toId){
            return String.format("%d_%d",fromId,toId);
        }
        return String.format("%d_%d",fromId,toId);
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }
    public String editConversationId(int fromId,int toId) {
        if(fromId<toId){
            return String.format("%d_%d",fromId,toId);
        }
        return String.format("%d_%d",fromId,toId);
    }
}
