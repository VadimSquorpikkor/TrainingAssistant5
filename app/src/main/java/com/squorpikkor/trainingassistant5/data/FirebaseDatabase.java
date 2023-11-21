package com.squorpikkor.trainingassistant5.data;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squorpikkor.trainingassistant5.SLog;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FirebaseDatabase {

    public static final String TAG = "fireBase";

    private final OnFailureListener onFailureListener = e -> Log.e(TAG, "Что-то пошло не так");

    private static final String TABLE_USER = "users";
    private static final String USER_ID = "id";
    private static final String USER_EMAIL = "name";
    private static final String USER_NAME = "name";

    private static final String TABLE_EXERCISES = "exercises";
    private static final String EXERCISES_PARENT = "parent_id";
    private static final String EXERCISE_NAME = "name";
    private static final String EXERCISE_BEST_ID = "best";
    private static final String EXERCISE_LAST_ID = "last";
    private static final String EXERCISE_PREDEFINED = "pred";

    //В контексте Firebase для Training и id, и date, и имя документа — это одно и то же значение,
    //поэтому при работе с этой БД будет использоваться только имя документа для получения и id, и даты
    private static final String TABLE_TRAINING = "trainings";
    private static final String TRAINING_PARENT = "parent_id";
    private static final String TRAINING_DATE = "date";
    private static final String TRAINING_WEIGHT = "weight";
    private static final String TRAINING_EFFECTIVITY = "eff";

    private static final String TABLE_EVENT = "events";
    private static final String EVENT_PARENT = "parent_id";
    private static final String EVENT_EX_ID = "ex_id";
    private static final String EVENT_SET = "set";
    private static final String EVENT_IS_COMPLETE = "is_complete";
    private static final String EVENT_RATE = "rate";

    private final FirebaseFirestore db;
    private final String login;

    public FirebaseDatabase(String login) {
        db = FirebaseFirestore.getInstance();
        this.login = login;
    }

    public abstract void onGetTrainings(ArrayList<Training> list);
    public abstract void onGetExercises(ArrayList<Exercise> list);
    public abstract void onGetEvents(ArrayList<Event> list);
    public abstract void onGetUserData(User user);
    public abstract void onGetLastEvent(Event event);
    public abstract void onGetBestEvent(Event event);

    private Event getEventByDocument(DocumentSnapshot document) {
        String id = document.getId();// String.valueOf(doc.get(EXERCISE_ID));
        String ex_id = String.valueOf(document.get(EVENT_EX_ID));
        String set = String.valueOf(document.get(EVENT_SET));
        String parent = String.valueOf(document.get(EVENT_PARENT));
        boolean isComplete = String.valueOf(document.get(EVENT_IS_COMPLETE)).equals("true");
        int rate = -1;
        try { rate = Integer.parseInt(String.valueOf(document.get(EVENT_RATE)));}
        catch (Exception ignored){}

        Event event = new Event(ex_id);
        event.setId(id);
        event.setParentId(parent);
        event.setWorkoutSet(set);
        event.setComplete(isComplete);
        event.setRate(rate);
        return event;
    }

    public void getLastEvent(String lastId) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EVENT).document(lastId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document!=null && document.exists()) {
                            onGetLastEvent(getEventByDocument(document));
                        } else {
                            Log.e(TAG, "No such document "+lastId);
                        }
                    } else {
                        Log.e(TAG, "get failed with ", task.getException());
                    }
                });
    }

    public void getBestEvent(String bestId) {
        SLog.e("best = "+bestId);
        if (bestId.equals("")) return;
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EVENT).document(bestId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document!=null && document.exists()) {
                            onGetBestEvent(getEventByDocument(document));
                        } else {
                            Log.e(TAG, "No such document "+bestId);
                        }
                    } else {
                        Log.e(TAG, "get failed with ", task.getException());
                    }
                });
    }

    public void getUserData() {
        db.collection(TABLE_USER).document(login)
        .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document!=null && document.exists()) {
                    Log.e(TAG, "DocumentSnapshot data: " + document.getData());
                    onGetUserData(new User(String.valueOf(document.get(USER_ID)), String.valueOf(document.get(USER_NAME))));
                } else {
                    Log.e(TAG, "No such document");
                }
            } else {
                Log.e(TAG, "get failed with ", task.getException());
            }
        });
    }

    public void getTrainings() {
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

    public void getExercises() {
        db.collection(TABLE_USER).document(login).collection(TABLE_EXERCISES)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult()==null) return;
                        ArrayList<Exercise> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = String.valueOf(document.get(EXERCISE_NAME));
                            Log.e(TAG, name);
                            String bestEventId = String.valueOf(document.get(EXERCISE_BEST_ID));
                            String lastEventId = String.valueOf(document.get(EXERCISE_LAST_ID));
                            String pred = String.valueOf(document.get(EXERCISE_PREDEFINED));
                            Log.e(TAG, pred);
                            int predefined = 0;
                            if (!pred.equals("null")) predefined = Integer.parseInt(pred);
                            Exercise exercise = new Exercise(name);
                            exercise.setId(document.getId());
                            exercise.setName(name);
                            exercise.setBestEventId(bestEventId);
                            exercise.setLastEventId(lastEventId);
                            exercise.setPredefinedTo(predefined);
                            list.add(exercise);
                        }
                        onGetExercises(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public void getEvents(String trainingId) {
        db.collection(TABLE_USER).document(login)

                .collection(TABLE_EVENT)
                .whereEqualTo(EVENT_PARENT, trainingId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult()==null) return;
                        ArrayList<Event> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Event event = getEventByDocument(document);
                            list.add(event);
                        }
                        onGetEvents(list);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    //если нужно несколько
//    db.collection("users").document("frank")
//        .update(mapOf(
//            "age" to 13,
//                "favorites.color" to "Red"
//    ))

    /***/
    public void updateWorkoutSet(Event event) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EVENT).document(event.getId())
                .update(EVENT_SET, event.getWorkoutSet()).addOnSuccessListener(unused -> Log.e(TAG, "updated successfully"));
    }

    /***/
    public void completeEvent(Event event) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EVENT).document(event.getId())
                .update(EVENT_IS_COMPLETE, event.isComplete()).addOnSuccessListener(unused -> Log.e(TAG, "updated successfully"));
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EVENT).document(event.getId())
                .update(EVENT_RATE, event.getRate()).addOnSuccessListener(unused -> Log.e(TAG, "updated successfully"));
    }


    /**Обновить id лучшего ивента данного упражнения. После обновления поля список упражнений будет перезагружен
     *
     * @param id идентификатор упражнения
     * @param bestId идентификатор лучшего ивента
     */
    public void updateExerciseBest(String id, String bestId) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EXERCISES).document(id)
                .update(EXERCISE_BEST_ID, bestId).addOnSuccessListener(unused -> getExercises());
    }

    /**Обновить id последнего ивента данного упражнения. После обновления поля список упражнений будет перезагружен
     *
     * @param id идентификатор упражнения
     * @param lastId идентификатор последнего ивента
     */
    public void updateExerciseLast(String id, String lastId) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EXERCISES).document(id)
                .update(EXERCISE_LAST_ID, lastId).addOnSuccessListener(unused -> getExercises());
    }

//--------------------------------------------------------------------------------------------------





    // TODO: 22.08.2023 rename
    public void connect(String user) {
         //userData = db.collection(TABLE_USER).document(user);
    }

    public void createUser() {
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
    public void addTraining(Training training, ArrayList<Event> list) {
        Map<String, Object> data = new HashMap<>();
        data.put(TRAINING_DATE, training.getDate());

        db.collection(TABLE_USER).document(training.getUserId()).collection(TABLE_TRAINING)
                .document(training.getDate())
                .set(data)
                .addOnSuccessListener(aVoid -> {

                    addNewTrainingListener();//todo а может без addNewTrainingListener, а сразу вызывать getTrainings(login);???
                    Log.e(TAG, "DocumentSnapshot successfully written!");
                    for (Event event : list) {
                        addEvent(event, training.getDate());
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    public void addExercise(Exercise exercise) {
        Map<String, Object> data = new HashMap<>();
        data.put(EXERCISE_NAME, exercise.getName());

        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EXERCISES).document()
                .set(data)
                .addOnSuccessListener(aVoid -> addNewExerciseListener())
                .addOnFailureListener(onFailureListener);
    }


    public void addEvent(Event event, String trainingId) {

        Map<String, Object> data = new HashMap<>();
        data.put(EVENT_EX_ID, event.getExerciseId());
        data.put(EVENT_PARENT, trainingId);

        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EVENT).document(event.getExerciseId())
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> addNewEventListener(trainingId))
                .addOnFailureListener(onFailureListener);
    }


//--------------------------------------------------------------------------------------------------

    void addNewTrainingListener() {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_TRAINING).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev0");
                    getTrainings();
                });
    }

    void addNewExerciseListener() {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EXERCISES).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev1");
                    getExercises();
                });
    }

    void addNewEventListener(String trainingId) {
        db.collection(TABLE_USER).document(login)
                .collection(TABLE_EVENT).addSnapshotListener((queryDocumentSnapshots, error) -> {
                    Log.e(TAG, "listen ev2");
                    getEvents(trainingId);
                });
    }


}
