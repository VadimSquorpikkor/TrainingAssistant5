package com.squorpikkor.trainingassistant5.entity;

/**Как было сделано конкретное упражнение: содержит список подходов (Sets)*/
public class Event extends BaseEntity {

    private String exerciseId;
    private String name;

    public Event(String exerciseId) {
        super();
        this.exerciseId = exerciseId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
