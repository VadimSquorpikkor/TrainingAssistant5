package com.squorpikkor.trainingassistant5;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squorpikkor.trainingassistant5.data.DataBaseHelper;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.User;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    public static final int PAGE_ALL_TRAININGS = 0;
    public static final int PAGE_TRAINING = 1;
    public static final int PAGE_EXERCISE = 2;

    private final MutableLiveData<ArrayList<Event>> events;
    private final MutableLiveData<ArrayList<Exercise>> exercises;
    private final MutableLiveData<ArrayList<Training>> trainings;

    private final MutableLiveData<Event> selectedEvent;
    private final MutableLiveData<Exercise> selectedExercise;
    private final MutableLiveData<Training> selectedTraining;

    private final MutableLiveData<Integer> selectedPage;

    private final DataBaseHelper db;

    public MainViewModel() {
        events = new MutableLiveData<>(new ArrayList<>());
        exercises = new MutableLiveData<>(new ArrayList<>());
        trainings = new MutableLiveData<>(new ArrayList<>());

        selectedTraining = new MutableLiveData<>();
        selectedExercise = new MutableLiveData<>();
        selectedEvent = new MutableLiveData<>();
        selectedPage = new MutableLiveData<>(PAGE_ALL_TRAININGS);
        db = new DataBaseHelper(events, exercises, trainings);
    }

    public MutableLiveData<ArrayList<Event>> getEvents() {
        return events;
    }
    public MutableLiveData<ArrayList<Exercise>> getExercises() {
        return exercises;
    }
    public MutableLiveData<ArrayList<Training>> getTrainings() {
        return trainings;
    }
    public MutableLiveData<Integer> getSelectedPage() {
        return selectedPage;
    }

    public void getTrainings(User user) {
        db.getTrainings(user);
    }

    public void getExercises(Training training) {
        selectedPage.postValue(PAGE_TRAINING);
        selectedTraining.postValue(training);
        db.getExercises(training);
    }

    public void getEvents(Exercise exercise) {
        selectedPage.postValue(PAGE_EXERCISE);
        selectedExercise.postValue(exercise);
        db.getEvents(exercise);
    }
}
