package com.squorpikkor.trainingassistant5.entity;

public class Event {
//    Event - id, exerciseId, data
    private final String id;
    private final String exerciseId;
    private final String data;

    public Event(String id, String exerciseId, String data) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.data = data;
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
}
