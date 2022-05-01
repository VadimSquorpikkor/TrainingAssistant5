package com.squorpikkor.trainingassistant5.entity;

public class Exercise {
//    Exercise - id, trainingId, name, bestEventId, lastEventId

    private final String id;
    private final String traininfId;
    private final String name;
    private final String bestEventId;
    private final String lastEventId;

    public Exercise(String id, String traininfId, String name, String bestEventId, String lastEventId) {
        this.id = id;
        this.traininfId = traininfId;
        this.name = name;
        this.bestEventId = bestEventId;
        this.lastEventId = lastEventId;
    }

    public String getId() {
        return id;
    }

    public String getTraininfId() {
        return traininfId;
    }

    public String getName() {
        return name;
    }

    public String getBestEventId() {
        return bestEventId;
    }

    public String getLastEventId() {
        return lastEventId;
    }
}
