package com.squorpikkor.trainingassistant5;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squorpikkor.trainingassistant5.data.DataBaseHelper;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Event>> selectedEvents;
    private final MutableLiveData<ArrayList<Exercise>> selectedExercise;
    private final MutableLiveData<ArrayList<Training>> selectedTraining;

    private final DataBaseHelper db;

    public MainViewModel() {
        selectedEvents   = new MutableLiveData<>(new ArrayList<>());
        selectedExercise = new MutableLiveData<>(new ArrayList<>());
        selectedTraining = new MutableLiveData<>(new ArrayList<>());
        db = new DataBaseHelper(selectedEvents, selectedExercise, selectedTraining);
    }

    public MutableLiveData<ArrayList<Event>> getSelectedEvents() {
        return selectedEvents;
    }
    public MutableLiveData<ArrayList<Exercise>> getSelectedExercise() {
        return selectedExercise;
    }
    public MutableLiveData<ArrayList<Training>> getSelectedTraining() {
        return selectedTraining;
    }

    public void selectTraining(Training training) {
        db.selectExerciseByTraining(training);
    }

    public void selectExercise(Exercise exercise) {

    }
}
