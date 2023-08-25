package com.squorpikkor.trainingassistant5.data;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** Точка общения с БД
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
public class FirebaseDatabase implements Data{

    private final OnFailureListener onFailureListener = e -> Log.e(TAG, "Что-то пошло не так");

    private static final String TABLE_EXERCISES = "exercises";
    private static final String EXERCISE_NAME = "name";
    private static final String EXERCISE_BEST_ID = "best";
    private static final String EXERCISE_LAST_ID = "last";
    private static final String EXERCISE_PREDEFINED = "pred";

    public static final String TABLE_USER = "users";

    private static final String TABLE_TRAINING = "trainings";
    private static final String TRAINING_DATE = "date";

    private static final String TABLE_EVENT = "events";
    private static final String EVENT_EX_ID = "ex_id";

    //private DocumentReference userData;


    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";

    public static final String TAG = "fireBase";
    private static final String TABLE_WORKOUT = "workouts";
    private static final String WORKOUT_DATA = "data";
    private static final String WORKOUT_ID = "id";

    private final FirebaseFirestore db;

    public FirebaseDatabase() {
        db = FirebaseFirestore.getInstance();
    }

    //private String login =











    private String selectedLogin;
    private String selectedTrainingId;
    private String selectedEventId;







    // TODO: 22.08.2023 rename
    public void connect(String user) {
         //userData = db.collection(TABLE_USER).document(user);
    }

    @Override
    public void createUser(String login) {
        if (login.equals("")) return;

        String userName = login.toLowerCase();
        Task<DocumentSnapshot> snapshotTask = db.collection(TABLE_USER).document(userName).get();
        snapshotTask.addOnCompleteListener(task -> {
            if (snapshotTask.getResult()!=null && snapshotTask.getResult().exists()) {
                Log.e(TAG, "есть уже такой");
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put(USER_ID, userName);
                data.put(USER_NAME, "имя");
                db.collection(TABLE_USER)
                        .document(userName)
                        .set(data)
                        .addOnSuccessListener(aVoid -> Log.e(TAG, "DocumentSnapshot successfully written!"))//todo toast
                        .addOnFailureListener(e -> Log.e(TAG, "Error writing document", e));//todo toast
            }
        });
    }


    /***/
    public interface OnTrainingResultListener {
        /***/
        void onResult(ArrayList<Training> list);
    }

    public void getTrainings(String login, OnTrainingResultListener onResultListener) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING)
                .get()
                .addOnCompleteListener(task -> {
                    Log.e(TAG, login);
                    if (task.isSuccessful()) {
                        if (task.getResult()==null) return;
                        ArrayList<Training> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();// String.valueOf(doc.get(EXERCISE_ID));
                            String date = String.valueOf(document.get(TRAINING_DATE));
                            Training training = new Training(login);
                            training.setId(id);
                            training.setDate(date);
                            list.add(training);
                        }
                        onResultListener.onResult(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void getTrainingsByUser(String login, MutableLiveData<ArrayList<Training>> trainings) {
        db.collection(TABLE_USER).document(login).collection(TABLE_TRAINING)
                .get()
                .addOnCompleteListener(task -> {
                    Log.e(TAG, login);
                    if (task.isSuccessful()) {
                        if (task.getResult()==null) return;
                        ArrayList<Training> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.e(TAG, "try");
                            String id = document.getId();// String.valueOf(doc.get(EXERCISE_ID));
                            String date = String.valueOf(document.get(TRAINING_DATE));
                            Training training = new Training(login);
                            training.setId(id);
                            training.setDate(date);
                            list.add(training);
                            Log.e(TAG, id);
                        }
                        trainings.setValue(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void getExerciseByUser(String login, MutableLiveData<ArrayList<Exercise>> exercises) {

        db.collection(TABLE_USER).document(login).collection(TABLE_EXERCISES)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult()==null) return;
                        ArrayList<Exercise> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();// String.valueOf(doc.get(EXERCISE_ID));
                            String name = String.valueOf(document.get(EXERCISE_NAME));
                            Log.e(TAG, name);
                            String bestEventId = String.valueOf(document.get(EXERCISE_BEST_ID));
                            String lastEventId = String.valueOf(document.get(EXERCISE_LAST_ID));
                            int predefined = Integer.parseInt(String.valueOf(document.get(EXERCISE_PREDEFINED)));
                            Exercise exercise = new Exercise(id, name, bestEventId, lastEventId, predefined);
                            list.add(exercise);
                        }
                        exercises.setValue(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void getEventByTraining(String login, String trainingId, MutableLiveData<ArrayList<Event>> events) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trainingId)
                .collection(TABLE_EVENT)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult()==null) return;
                        ArrayList<Event> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();// String.valueOf(doc.get(EXERCISE_ID));
                            String ex_id = String.valueOf(document.get(EVENT_EX_ID));
                            Event event = new Event(ex_id);
                            event.setId(id);
                            event.setTrainingId(trainingId);
                            list.add(event);
                        }
                        events.setValue(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void getSetsByEvent(String login, Event event, MutableLiveData<ArrayList<WorkoutSet>> sets) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(event.getTrainingId())
                .collection(TABLE_EVENT).document(event.getId())
                .collection(TABLE_WORKOUT)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult()==null) return;
                        ArrayList<WorkoutSet> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String data = String.valueOf(document.get(WORKOUT_DATA));
                            WorkoutSet workoutSet = new WorkoutSet(data);
                            list.add(workoutSet);
                        }
                        sets.setValue(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void addEventsList(ArrayList<Event> list, MutableLiveData<ArrayList<Event>> events) {
//        for (Event event : list) {
//            event.setTrainingId(trId);
//            addEvent(event, training.getUserId());
//        }
//        addNewEventListener(training.getUserId(), trId, events);
    }



    @Override
    public void addTraining(Training training, OnSuccessListener<Void> listener) {
        Date trDate = new Date();
        String trId = String.valueOf(trDate.getTime());

        Map<String, Object> data = new HashMap<>();
        data.put(TRAINING_DATE, trDate);

        db.collection(TABLE_USER).document(training.getUserId()).collection(TABLE_TRAINING)
                .document(trId)
                .set(data)
                .addOnSuccessListener(listener)
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void addTraining_old(Training training, ArrayList<Event> list, MutableLiveData<ArrayList<Event>> events) {
        Date trDate = new Date();
        String trId = String.valueOf(trDate.getTime());
        training.setId(trId);

        Map<String, Object> data = new HashMap<>();
        data.put(TRAINING_DATE, trId);

        db.collection(TABLE_USER).document(training.getUserId()).collection(TABLE_TRAINING)
                .document(trId)
                .set(data)
                .addOnSuccessListener(aVoid -> {

                    Log.e(TAG, "DocumentSnapshot successfully written!");
                    for (Event event : list) {
                        event.setTrainingId(trId);
                        addEvent(event, training.getUserId());
                    }
                    //////////////////////////////FirebaseDatabase.this.addNewEventListener_old(training.getUserId(), training, events);

                })
                .addOnFailureListener(onFailureListener);
    }

//    abstract public void TrainingAddComplete();

    @Override
    public void addExercise(Exercise exercise) {
        if (exercise.equals("")) return;



        /*Task<DocumentSnapshot> snapshotTask = db.collection(TABLE_USER).document(exercise.getParentId()).collection(TABLE_EXERCISES).get();
        snapshotTask.addOnCompleteListener(task -> {
            if (snapshotTask.getResult()!=null && snapshotTask.getResult().exists()) {
                Log.e(TAG, "есть уже такой");
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put(USER_ID, userName);
                data.put(USER_NAME, "имя");
                db.collection(TABLE_USER)
                        .document(userName)
                        .set(data)
                        .addOnSuccessListener(aVoid -> Log.e(TAG, "DocumentSnapshot successfully written!"))//todo toast
                        .addOnFailureListener(e -> Log.e(TAG, "Error writing document", e));//todo toast
            }
        });*/
    }

    public interface OnSuccess {
        void onSuccess();
    }

    public void addEventNew(Event event, String login, OnSuccess onSuccess) {

        Map<String, Object> data = new HashMap<>();
        data.put(EVENT_EX_ID, event.getExerciseId());

        Log.e(TAG, event.getExerciseId());
        Log.e(TAG, event.getTrainingId());

        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(event.getTrainingId())
                .collection(TABLE_EVENT).document(event.getExerciseId())
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    addNewEventListener_new(login, event.getTrainingId(), onSuccess);
                    Log.e(TAG, "addEvent DocumentSnapshot successfully written!");
                })//todo toast
                .addOnFailureListener(e -> Log.e(TAG, "addEvent Error writing document", e));//todo toast
    }

    @Override
    public void addEvent(Event event, String login) {

        Map<String, Object> data = new HashMap<>();
        data.put(EVENT_EX_ID, event.getExerciseId());

        Log.e(TAG, event.getExerciseId());
        Log.e(TAG, event.getTrainingId());

        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(event.getTrainingId())
                .collection(TABLE_EVENT).document(event.getExerciseId())
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.e(TAG, "addEvent DocumentSnapshot successfully written!"))//todo toast
                .addOnFailureListener(e -> Log.e(TAG, "addEvent Error writing document", e));//todo toast
    }

    @Override
    public void addSet(String login, String trId, String evId, WorkoutSet workoutSet, OnSuccess onSuccess) {
        Date trDate = new Date();
        String setId = String.valueOf(trDate.getTime());

        Map<String, Object> data = new HashMap<>();
        data.put(WORKOUT_DATA, workoutSet.getData());
        data.put(WORKOUT_ID, setId);

        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trId)
                .collection(TABLE_EVENT).document(evId)
                .collection(TABLE_WORKOUT).document(setId)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    addNewWorkoutListener(login, trId, onSuccess);
                    Log.e(TAG, "addEvent DocumentSnapshot successfully written!");
                })//todo toast
                .addOnFailureListener(e -> Log.e(TAG, "addEvent Error writing document", e));//todo toast
    }

    @Override
    public void setMutableForMessage(MutableLiveData<String> messages) {

    }

    @Override
    public void setMessage(String message) {

    }

    /**Слушатель для новых событий*/
    void addTrainingsListener(String login) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).addSnapshotListener((queryDocumentSnapshots, error) -> Log.e(TAG, "listen tr"));
    }
//--------------------------------------------------------------------------------------------------
    private EventListener<QuerySnapshot> eventListener;

    public void setEventListener(EventListener<QuerySnapshot> eventListener) {
        this.eventListener = eventListener;
    }

    /**Слушатель для новых событий*/
    void addNewEventListener(String login, Training training) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(training.getId())
                .collection(TABLE_EVENT).addSnapshotListener(eventListener);
    }

    void addNewEventListener_old(String login, Training training, MutableLiveData<ArrayList<Event>> events) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(training.getId())
                .collection(TABLE_EVENT).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        Log.e(TAG, "listen ev");
                        getEventByTraining(training.getUserId(), training.getId(), events);

                    }
                });
    }

    void addNewWorkoutListener_old(String login, String trainingId, MutableLiveData<ArrayList<Event>> events) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trainingId)
                .collection(TABLE_EVENT).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev");
                    getEventByTraining(login, trainingId, events);

                });
    }

    void addNewWorkoutListener(String login, String trainingId, OnSuccess onSuccess) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trainingId)
                .collection(TABLE_EVENT).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev");
                    onSuccess.onSuccess();
                });
    }

    void addNewEventListener_new(String login, String trainingId, OnSuccess onSuccess) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trainingId)
                .collection(TABLE_EVENT).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev");
                    onSuccess.onSuccess();
                });
    }
}
