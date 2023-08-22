package com.squorpikkor.trainingassistant5.data;

import androidx.lifecycle.MutableLiveData;

import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.User;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

import java.util.ArrayList;

/** Точка общения с БД
 *
 *     старый вариант (для сравнения и чтобы помнить старые имена)
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
 *--------------------------------------------------------------------------------------------------
 *
 *     старый вариант 2 вариант
 *     ____      ____      ____      ____      ____
 *     |  |      |  |      |  |      |  |      |  |
 *     |  |-----<|  |-----<|  |-----<|  |-----<|  |
 *     |  |      |  |      |  |      |  |      |  |
 *     Users   Training  Exercise    Event     WorkoutSet
 *
 *     Users    - id, name, login, password
 *     Training - id, date, userId
 *     Exercise - id, trainingId, name, bestEventId, lastEventId
 *     Event    - id, exerciseId, data
 *     WorkoutSet      - id, eventId, data
 *
 * -------------------------------------------------------------------------------------------------
 *
 *      актуальный вариант
 *      ____         ____      ____      ____
 *      |  |         |  |      |  |      |  |
 *      |  |--------<|  |-----<|  |-----<|  |
 *      |  |         |  |      |  |      |  |
 *      Users      Training    Event   WorkoutSet
 *        |          ____
 *        |          |  |
 *        \---------<|  |
 *                   |  |
 *                 Exercise
 *
 *      Users      - id, name, login, password
 *      Training   - id, date, userId
 *      Exercise   - id, userId, name, bestEventId, lastEventId
 *      Event      - id, exerciseId, data
 *      WorkoutSet - id, eventId, data
 *
 *
 *
 * */

// TODO: 22.08.2023 удалить класс -- не нужен, напрямую сразу к FB
public class DataBaseHelper  implements Data{

    private final Data db;

    public DataBaseHelper() {
        this.db = new FirebaseDatabase();
    }

    @Override
    public void createUser(String userName) {
        db.createUser(userName);
    }

    public void connect(String user) {
        db.connect(user);
    }

    @Override
    public void getTrainingsByUser(User user, MutableLiveData<ArrayList<Training>> trainings) {
        db.getTrainingsByUser(user, trainings);
    }

    @Override
    public void getExerciseByUser(String login, MutableLiveData<ArrayList<Exercise>> exercises) {
        db.getExerciseByUser(login, exercises);
    }

    @Override
    public void getEventByTraining(Training training, MutableLiveData<ArrayList<Event>> events) {
        db.getEventByTraining(training, events);
    }

    @Override
    public void getSetsByEvent(Event event, MutableLiveData<ArrayList<WorkoutSet>> sets) {

    }

    @Override
    public void addEventsList(ArrayList<Event> list, MutableLiveData<ArrayList<Event>> events) {

    }


    @Override
    public void addTraining(Training training, ArrayList<Event> list) {
        db.addTraining(training, list);
    }

    @Override
    public void addExercise(Exercise exercise) {

    }

    @Override
    public void addEvent(Event event) {

    }

    @Override
    public void addSet(WorkoutSet workoutSet) {

    }

    @Override
    public void setMutableForMessage(MutableLiveData<String> messages) {

    }

    @Override
    public void setMessage(String message) {

    }

}
