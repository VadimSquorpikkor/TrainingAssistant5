package com.squorpikkor.trainingassistant5.data;

import androidx.lifecycle.MutableLiveData;

import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;

import java.util.ArrayList;

/** Точка общения с БД
 *
 *     ____      ____      ____      ____
 *     |  |      |  |      |  |      |  |
 *     |  |-----<|  |-----<|  |-----<|  |
 *     |  |      |  |      |  |      |  |
 *     Users   Training  Exercise    Event
 *
 *     Users - id, name, login, password
 *     Training - id, date, userId
 *     Exercise - id, trainingId, name, bestEventId, lastEventId
 *     Event - id, exerciseId, data
 *
 *     для всех таблиц (коллекций) идентификатор (он же primary key) — это имя документа
 *
 *
 * */
public class DataBaseHelper {

    private final MutableLiveData<ArrayList<Event>> selectedEvents;
    private final MutableLiveData<ArrayList<Exercise>> selectedExercise;
    private final MutableLiveData<ArrayList<Training>> selectedTraining;

    public DataBaseHelper(MutableLiveData<ArrayList<Event>> selectedEvents,
                          MutableLiveData<ArrayList<Exercise>> selectedExercise,
                          MutableLiveData<ArrayList<Training>> selectedTraining) {
        this.selectedEvents = selectedEvents;
        this.selectedExercise = selectedExercise;
        this.selectedTraining = selectedTraining;
    }

    public void selectExerciseByTraining(Training training) {

    }
}
