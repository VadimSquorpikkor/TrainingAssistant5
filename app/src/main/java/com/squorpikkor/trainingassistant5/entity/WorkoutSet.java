package com.squorpikkor.trainingassistant5.entity;

import java.util.ArrayList;

/**
 * Подход
 */
public class WorkoutSet {

   WorkoutType workoutType;//это сет для бега или обычный

   enum WorkoutType{
      RUNNING_SET,
      ORDINARY_SET
   }

   boolean isRunningSet;//true если это сет для бега

   //для силового подхода
   float weight;
   int count;

   //для бега
   int distance;
   float velocity;
   int angle;

   public WorkoutSet(float weight, int count) {
      this.weight = weight;
      this.count = count;
      workoutType = WorkoutType.RUNNING_SET;
   }

   public WorkoutSet(int distance, float velocity, int angle) {
      this.distance = distance;
      this.velocity = velocity;
      this.angle = angle;
      workoutType = WorkoutType.RUNNING_SET;
   }

   public WorkoutType getWorkoutType() {
      return workoutType;
   }

   public float getWeight() {
      return weight;
   }

   public int getCount() {
      return count;
   }

   public int getDistance() {
      return distance;
   }

   public float getVelocity() {
      return velocity;
   }

   public int getAngle() {
      return angle;
   }




}
