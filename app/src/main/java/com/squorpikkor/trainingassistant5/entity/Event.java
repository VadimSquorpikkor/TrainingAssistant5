package com.squorpikkor.trainingassistant5.entity;

import java.util.Date;

/**Как было сделано конкретное упражнение: содержит список подходов (Sets)*/
public class Event {
//    Event - id, exerciseId, data
    private String id;
    private final String exerciseId;
    private String trainingId;
    private String data;
    private String name;

    public Event(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getId() {
        return id;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public String getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }
}
