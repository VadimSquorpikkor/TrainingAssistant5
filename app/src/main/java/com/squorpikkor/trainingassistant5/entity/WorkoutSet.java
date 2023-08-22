package com.squorpikkor.trainingassistant5.entity;

/**Подход*/
public class WorkoutSet {// TODO: 21.08.2023 rename

   private final String id;
   private final String eventId;
   private final String data;

   public WorkoutSet(String id, String eventId, String data) {
      this.id = id;
      this.eventId = eventId;
      this.data = data;
   }

   public String getId() {
      return id;
   }

   public String getEventId() {
      return eventId;
   }

   public String getData() {
      return data;
   }
}
