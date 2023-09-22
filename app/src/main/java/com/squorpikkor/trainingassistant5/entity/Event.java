package com.squorpikkor.trainingassistant5.entity;

import com.squorpikkor.trainingassistant5.Utils;

import java.util.ArrayList;

/**Как было сделано конкретное упражнение: содержит список подходов (WorkoutSets)*/
public class Event extends BaseEntity {

    private String exerciseId;
    private String name;
    private String workoutSet;

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

    public String getWorkoutSet() {
        return workoutSet;
    }

    public void setWorkoutSet(String workoutSet) {
        this.workoutSet = workoutSet;
    }




    public ArrayList<WorkoutSet> getWorkoutSetList() {
        return parseSetFromString(workoutSet);
    }

    /**Добавить подход*/
    public void addSet(WorkoutSet set) {
        workoutSet = workoutSet+" "+ parseWorkoutToString(set);
    }

    private String parseWorkoutToString(WorkoutSet workoutSet) {
        return workoutSet.getWeight()+"x"+ workoutSet.getCount();
    }

    /**"60x10 70x10 80x5 70x6" ->>> ArrayList<WorkoutSet>*/
    private ArrayList<WorkoutSet> parseSetFromString(String workoutSetString) {
        String[] arr = workoutSetString.split(" ");
        ArrayList<WorkoutSet> res = new ArrayList<>();
        if (arr.length==0) return res;
        for (String s:arr) {
            String[] set = s.split("x");
            if (set.length > 1) {
                float weight = Float.parseFloat(set[0]);
                int count = Integer.parseInt(set[1]);
                res.add(new WorkoutSet(weight, count));
            }
        }
        return res;
    }

    /**"1600x11x2" ->>> ArrayList<WorkoutSet>*/
    private ArrayList<WorkoutSet> parseRunningSetFromString(String workoutSetString) {
        String[] arr = workoutSetString.split(" ");
        ArrayList<WorkoutSet> res = new ArrayList<>();
        for (String s:arr) {
            String[] set = s.split("x");
            int distance = Integer.parseInt(set[0]);
            float velocity = Float.parseFloat(set[1]);
            int angle = Integer.parseInt(set[2]);
            res.add(new WorkoutSet(distance, velocity, angle));
        }
        return res;
    }
}
