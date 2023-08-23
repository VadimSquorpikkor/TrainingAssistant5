package com.squorpikkor.trainingassistant5.entity;

import java.util.Date;

/**Тренировка. Тот список упражнений, который нужно сделать в зале в конкретный день*/
public class Training {
//    Training - id, date, userId

    private String id;
    private final String userId;
    private Date Date;

    public Training(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }// TODO: 23.08.2023 нахер нужно

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
