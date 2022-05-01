package com.squorpikkor.trainingassistant5.entity;

import java.util.Date;

public class Training {
//    Training - id, date, userId

    private final String id;
    private final Date date;
    private final String userId;

    public Training(String id, Date date, String userId) {
        this.id = id;
        this.date = date;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }
}
