package com.squorpikkor.trainingassistant5.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.User;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

class FirebaseDatabase implements Data{

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

    private final FirebaseFirestore db;

    public FirebaseDatabase() {
        db = FirebaseFirestore.getInstance();
    }

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


    @Override
    public void getTrainingsByUser(User user, MutableLiveData<ArrayList<Training>> trainings) {

    }

    @Override
    public void getExerciseByUser(String login, MutableLiveData<ArrayList<Exercise>> exercises) {

        //Query query = userData.collection(TABLE_EXERCISES);
        Query query = db.collection(TABLE_USER).document(login).collection(TABLE_EXERCISES);

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot == null) return;
                        if (querySnapshot.isEmpty()) {
                            Log.e(TAG, "3");
                            return;
                        }

                        db.collection(TABLE_USER).document(login).get().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                DocumentSnapshot document = task2.getResult();
                                if (document == null) return;
                                if (querySnapshot.isEmpty()) return;
                                if (document.exists()) {

                                    ArrayList<Exercise> list = new ArrayList<>();
                                    for (DocumentSnapshot doc : task.getResult()) {
                                        String id = doc.getId();// String.valueOf(doc.get(EXERCISE_ID));
                                        String name = String.valueOf(doc.get(EXERCISE_NAME));
                                        String bestEventId = String.valueOf(doc.get(EXERCISE_BEST_ID));
                                        String lastEventId = String.valueOf(doc.get(EXERCISE_LAST_ID));
                                        int predefined = Integer.parseInt(String.valueOf(doc.get(EXERCISE_PREDEFINED)));
                                        Exercise exercise = new Exercise(id, name, bestEventId, lastEventId, predefined);
                                        list.add(exercise);
                                    }
                                    exercises.setValue(list);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public void getEventByTraining(Training training, MutableLiveData<ArrayList<Event>> events) {

    }

    @Override
    public void getSetsByEvent(Event event, MutableLiveData<ArrayList<WorkoutSet>> sets) {

    }

    @Override
    public void addEventsList(ArrayList<Event> list, MutableLiveData<ArrayList<Event>> events) {

    }

    @Override
    public void addTraining(Training training, ArrayList<Event> list) {
        Map<String, Object> data = new HashMap<>();
        data.put(TRAINING_DATE, new Date());
        db.collection(TABLE_USER).document(training.getUserId()).collection(TABLE_TRAINING)
                .document(String.valueOf(new Date().getTime()))
                .set(data)
                .addOnSuccessListener(aVoid -> {






                    Log.e(TAG, "DocumentSnapshot successfully written!");
                    for (Event event:list) {
                        addEvent(event);
                    }
                })//todo toast
                .addOnFailureListener(e -> Log.e(TAG, "Error writing document", e));//todo toast
    }

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

    @Override
    public void addEvent(Event event) {

        Map<String, Object> data = new HashMap<>();
        data.put(EVENT_EX_ID, event.getExerciseId());
        db.collection(TABLE_USER).document(event.getTrainingId()).collection(TABLE_TRAINING).document(event.getTrainingId()).collection(TABLE_EVENT)
                .document(String.valueOf(new Date()))
                .set(data)
                .addOnSuccessListener(aVoid -> Log.e(TAG, "DocumentSnapshot successfully written!"))//todo toast
                .addOnFailureListener(e -> Log.e(TAG, "Error writing document", e));//todo toast
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
