package com.squorpikkor.trainingassistant5.data;

import androidx.lifecycle.MutableLiveData;

import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.User;

import java.util.ArrayList;

class FirebaseDatabase implements Data{


    @Override
    public void getTrainingsByUser(User user, MutableLiveData<ArrayList<Training>> trainings) {

    }

    @Override
    public void getExerciseByTraining(Training training, MutableLiveData<ArrayList<Exercise>> exercises) {

    }

    @Override
    public void getEventByExercise(Exercise exercise, MutableLiveData<ArrayList<Event>> events) {

    }

    @Override
    public void addTraining(Training training) {

    }

    @Override
    public void addExercise(Exercise exercise) {

    }

    @Override
    public void addEvent(Event event) {

    }

    @Override
    public void setMutableForMessage(MutableLiveData<String> messages) {

    }

    @Override
    public void setMessage(String message) {

    }
}
