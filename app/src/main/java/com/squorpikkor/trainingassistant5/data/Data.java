package com.squorpikkor.trainingassistant5.data;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnSuccessListener;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.User;

import java.util.ArrayList;

interface Data {

    void createUser(String userName);

    void connect(String user);

    /**Все тренировки пользователя*/
    void getTrainingsByUser(String login, MutableLiveData<ArrayList<Training>> trainings);

    void getExerciseByUser(String login, MutableLiveData<ArrayList<Exercise>> exercises);

    void getEventByTraining(String login, Training training, MutableLiveData<ArrayList<Event>> events);

    void getSetsByEvent(String login, Event event, MutableLiveData<ArrayList<WorkoutSet>> sets);


    void addEventsList(ArrayList<Event> list, MutableLiveData<ArrayList<Event>> events);


    void addTraining(Training training, OnSuccessListener<Void> listener);

    void addTraining_old(Training training, ArrayList<Event> list, MutableLiveData<ArrayList<Event>> events);

    void addExercise(Exercise exercise);

    void addEvent(Event event, String login);

    void addSet(WorkoutSet workoutSet);

    /**При старте дать БД мутабл, в который будут отправляться сообщения*/
    void setMutableForMessage(MutableLiveData<String> messages);// TODO: 15.08.2023 или сделать листом

    void setMessage(String message);


}
