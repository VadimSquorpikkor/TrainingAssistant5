package com.squorpikkor.trainingassistant5.entity;

/**Тренировка. Тот список упражнений, который нужно сделать в зале в конкретный день*/
public class Training extends BaseEntity {

    private float weight;
    private String effectivity;
    private String effPoints;
    private final String userId;


    public Training(String userId) {
        super();
        this.userId = userId;
    }

    /**Вес пользователя*/
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getEffectivity() {
        return effectivity;
    }

    public void setEffectivity(String effectivity) {
        this.effectivity = effectivity;
    }

    public String getUserId() {
        return userId;
    }// TODO: 23.08.2023 нахер нужно

}
