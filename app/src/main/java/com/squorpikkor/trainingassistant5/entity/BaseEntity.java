package com.squorpikkor.trainingassistant5.entity;

import java.util.Date;

public class BaseEntity {

   String id;
   String parentId;
   String date;

    public BaseEntity() {
        String date = String.valueOf(new Date().getTime());
        this.id = date;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
