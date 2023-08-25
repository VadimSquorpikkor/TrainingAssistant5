package com.squorpikkor.trainingassistant5.entity;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class Entity {

   private MutableLiveData<ArrayList<Training>> trainings;
   private MutableLiveData<ArrayList<Event>> events;
   private MutableLiveData<ArrayList<WorkoutSet>> workoutSets;
   private String login;
   private String trainingId;
   private String eventId;
   private String workoutId;


   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   public MutableLiveData<ArrayList<Training>> getTrainings() {
      return trainings;
   }

   public void setTrainings(MutableLiveData<ArrayList<Training>> trainings) {
      this.trainings = trainings;
   }

   public MutableLiveData<ArrayList<Event>> getEvents() {
      return events;
   }

   public void setEvents(MutableLiveData<ArrayList<Event>> events) {
      this.events = events;
   }

   public MutableLiveData<ArrayList<WorkoutSet>> getWorkoutSets() {
      return workoutSets;
   }

   public void setWorkoutSets(MutableLiveData<ArrayList<WorkoutSet>> workoutSets) {
      this.workoutSets = workoutSets;
   }

   public String getTrainingId() {
      return trainingId;
   }

   public void setTrainingId(String trainingId) {
      this.trainingId = trainingId;
   }

   public String getEventId() {
      return eventId;
   }

   public void setEventId(String eventId) {
      this.eventId = eventId;
   }

   public String getWorkoutId() {
      return workoutId;
   }

   public void setWorkoutId(String workoutId) {
      this.workoutId = workoutId;
   }
}
