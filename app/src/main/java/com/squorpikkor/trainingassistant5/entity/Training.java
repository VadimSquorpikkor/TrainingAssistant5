package com.squorpikkor.trainingassistant5.entity;


import com.squorpikkor.trainingassistant5.Utils;

/**Тренировка. Тот список упражнений, который нужно сделать в зале в конкретный день*/
public class Training extends BaseEntity {

    private float weight;
    private String effectivity;
    private String date;


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





    private final String userId;

    public Training(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }// TODO: 23.08.2023 нахер нужно

    public String getDate() {
        return Utils.getDateStringFromLongString(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

}
