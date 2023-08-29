package com.squorpikkor.trainingassistant5.entity;

/**Подход*/
public class WorkoutSet extends BaseEntity{// TODO: 21.08.2023 rename

   private String data;

   public WorkoutSet(String data) {
      this.data = data;
   }

   public String getData() {
      return data;
   }

   public void setData(String data) {
      this.data = data;
   }
}
