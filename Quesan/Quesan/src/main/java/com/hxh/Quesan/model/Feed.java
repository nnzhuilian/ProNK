package com.hxh.Quesan.model;

import java.util.Date;

public class Feed {
    private int id;
    private int type;//新鲜事类型
    private int userId;
    private Date createdDate;
    private String data;//用于存储Json，因为每种新鲜事的类型都不一样，需要的参数都不一样，因此存储在Json中。

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
