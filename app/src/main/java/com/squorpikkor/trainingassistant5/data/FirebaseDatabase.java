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
import com.squorpikkor.trainingassistant5.entity.BaseEntity;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**

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
public abstract class FirebaseDatabase {

    public static final String TAG = "fireBase";

    private final OnFailureListener onFailureListener = e -> Log.e(TAG, "Что-то пошло не так");

    private static final String TABLE_EXERCISES = "exercises";
    private static final String EXERCISE_NAME = "name";
    private static final String EXERCISE_BEST_ID = "best";
    private static final String EXERCISE_LAST_ID = "last";
    private static final String EXERCISE_PREDEFINED = "pred";

    private static final String TABLE_USER = "users";
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";

    private static final String TABLE_TRAINING = "trainings";
    private static final String TRAINING_DATE = "date";

    private static final String TABLE_EVENT = "events";
    private static final String EVENT_EX_ID = "ex_id";

    private static final String TABLE_WORKOUT = "workouts";
    private static final String WORKOUT_DATA = "data";
    private static final String WORKOUT_ID = "id";

    private final FirebaseFirestore db;

    public FirebaseDatabase() {
        db = FirebaseFirestore.getInstance();
    }


    public abstract void onGetTrainings(ArrayList<Training> list);
    public abstract void onGetExercises(ArrayList<Exercise> list);
    public abstract void onGetEvents(ArrayList<Event> list);
    public abstract void onGetWorkouts(ArrayList<WorkoutSet> list);


    public void getTrainings(String login) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING)
                .get()
                .addOnCompleteListener(task -> {
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
                        onGetTrainings(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void getExercises(String login) {
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
                        onGetExercises(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void getEvents(String login, String trainingId) {
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
                        onGetEvents(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void getWorkouts(String login, String trainingId, String eventId) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trainingId)
                .collection(TABLE_EVENT).document(eventId)
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
                        onGetWorkouts(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }
//--------------------------------------------------------------------------------------------------














    // TODO: 22.08.2023 rename
    public void connect(String user) {
         //userData = db.collection(TABLE_USER).document(user);
    }

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



//--------------------------------------------------------------------------------------------------
    public void addTraining(Training training, ArrayList<Event> list, String login) {
        Date trDate = new Date();
        String trainingId = String.valueOf(trDate.getTime());
        training.setId(trainingId);

        Map<String, Object> data = new HashMap<>();
        data.put(TRAINING_DATE, trainingId);


        db.collection(TABLE_USER).document(training.getUserId()).collection(TABLE_TRAINING)
                .document(trainingId)
                .set(data)
                .addOnSuccessListener(aVoid -> {

                    addNewTrainingListener(login);//todo а может без addNewTrainingListener, а сразу вызывать getTrainings(login);???
                    Log.e(TAG, "DocumentSnapshot successfully written!");
                    for (Event event : list) {
                        addEvent(event, login, trainingId);
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    public void addExercise(Exercise exercise, String login) {
        Map<String, Object> data = new HashMap<>();
        data.put(EXERCISE_NAME, exercise.getName());

        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EXERCISES).document()
                .set(data)
                .addOnSuccessListener(aVoid -> addNewExerciseListener(login))
                .addOnFailureListener(e -> Log.e(TAG, "addEvent Error writing document", e));//todo toast
    }


    public void addEvent(Event event, String login, String trainingId) {

        Map<String, Object> data = new HashMap<>();
        data.put(EVENT_EX_ID, event.getExerciseId());

        Log.e(TAG, event.getExerciseId());
        Log.e(TAG, event.getTrainingId());

        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trainingId)
                .collection(TABLE_EVENT).document(event.getExerciseId())
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> addNewEventListener(login, trainingId))
                .addOnFailureListener(e -> Log.e(TAG, "addEvent Error writing document", e));//todo toast
    }

    public void addWorkout(WorkoutSet workoutSet, String login, String trId, String evId) {
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
                    addNewWorkoutListener(login, trId, evId);
                    Log.e(TAG, "addEvent DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e -> Log.e(TAG, "addEvent Error writing document", e));//todo toast
    }

//--------------------------------------------------------------------------------------------------

    void addNewTrainingListener(String login) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev0");
                    getTrainings(login);
                });
    }

    void addNewExerciseListener(String login) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EXERCISES).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev1");
                    getExercises(login);
                });
    }

    void addNewEventListener(String login, String trainingId) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trainingId)
                .collection(TABLE_EVENT).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev2");
                    getEvents(login, trainingId);
                });
    }

    void addNewWorkoutListener(String login, String trainingId, String eventId) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).document(trainingId)
                .collection(TABLE_EVENT).document(eventId)
                .collection(TABLE_WORKOUT).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev3");
                    getWorkouts(login, trainingId, eventId);
                });
    }
}
