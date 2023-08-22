package com.squorpikkor.trainingassistant5.entity;

import java.util.Date;

/**Тренировка. Тот список упражнений, который нужно сделать в зале в конкретный день*/
public class Training {
//    Training - id, date, userId

    private final String userId;
    private String Date;

    public Training(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
