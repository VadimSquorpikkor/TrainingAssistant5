package com.squorpikkor.trainingassistant5.data;

import androidx.lifecycle.MutableLiveData;

import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.User;

import java.util.ArrayList;

interface Data {

    void getTrainingsByUser(User user, MutableLiveData<ArrayList<Training>> trainings);

    void getExerciseByTraining(Training training, MutableLiveData<ArrayList<Exercise>> exercises);

    void getEventByExercise(Exercise exercise, MutableLiveData<ArrayList<Event>> events);

    void addTraining(Training training);

    void addExercise(Exercise exercise);

    void addEvent(Event event);

    /**При старте дать БД мутабл, в который будут отправляться сообщения*/
    void setMutableForMessage(MutableLiveData<String> messages);// TODO: 15.08.2023 или сделать листом

    void setMessage(String message);


}
