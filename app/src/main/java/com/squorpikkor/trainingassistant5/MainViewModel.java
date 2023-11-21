package com.squorpikkor.trainingassistant5;

import static com.squorpikkor.trainingassistant5.Utils.BEST;
import static com.squorpikkor.trainingassistant5.Utils.BEST_NEW_WEIGHT;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.squorpikkor.trainingassistant5.data.FirebaseDatabase;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
import com.squorpikkor.trainingassistant5.entity.User;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

import java.util.ArrayList;
import java.util.HashMap;

public class MainViewModel extends ViewModel {

    public static final int PAGE_ALL_TRAININGS = 0;
    public static final int PAGE_TRAINING = 1;
    public static final int PAGE_EXERCISE = 2;

    private final MutableLiveData<ArrayList<Training>> trainings;
    private final MutableLiveData<ArrayList<Exercise>> exercises;
    private final MutableLiveData<ArrayList<Event>> events;

    // TODO: 24.08.2023 мютабл не нужен. Простые классы. или вообще удалить
    //Entities
    private final MutableLiveData<Training> selectedTraining;
    private final MutableLiveData<Integer> selectedEvent;
    private final MutableLiveData<Event> lastEvent;
    private final MutableLiveData<Event> bestEvent;

    private final MutableLiveData<Integer> selectedPage;

    //SignIn
    public static final int NO_ACTION = 0;
    public static final int REGISTER_SUCCESS = 1;
    public static final int REGISTER_FAILURE = 2;
    public static final int SIGN_IN_SUCCESS = 3;
    public static final int SIGN_IN_FAILURE = 4;
    private final MutableLiveData<String> login;
    private final MutableLiveData<String> signedLogin;
    private final MutableLiveData<String> password;
    private final MutableLiveData<Integer> loginState;

    private FirebaseDatabase db;
    private final FireAuth fireAuth;
    private final HashMap<String, String> exerciseDictionary;

    public MainViewModel() {
        exerciseDictionary = new HashMap<>();
        trainings = new MutableLiveData<>(new ArrayList<>());
        exercises = new MutableLiveData<>(new ArrayList<>());
        events = new MutableLiveData<>(new ArrayList<>());
        lastEvent = new MutableLiveData<>();
        bestEvent = new MutableLiveData<>();

        selectedTraining = new MutableLiveData<>();
        selectedEvent = new MutableLiveData<>();

        selectedPage = new MutableLiveData<>(PAGE_ALL_TRAININGS);
        login = new MutableLiveData<>("VadimSerikov11@gmail.com");// TODO: 17.08.2023 загружать из pref, по умолчанию будет ""
        signedLogin = new MutableLiveData<>("Вход не выполнен");
        password = new MutableLiveData<>("123123");// TODO: 17.08.2023
        loginState = new MutableLiveData<>(NO_ACTION);



        // TODO: 22.08.2023 перенести в БД
        fireAuth = new FireAuth() {
            @Override public void onRegisterInSuccess() {
                loginState.postValue(REGISTER_SUCCESS);
                if (FirebaseAuth.getInstance().getCurrentUser()!=null)
                    signedLogin.setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            }
            @Override public void onRegisterFailure() {
                loginState.postValue(REGISTER_FAILURE);
            }
            @Override public void onSignInSuccess() {
                loginState.postValue(SIGN_IN_SUCCESS);
                if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    signedLogin.setValue(email);
                    initFireDB(email);
                    loadAllTrainings();
                    loadAllExercises();
                    getUserData();
                }

            }
            @Override public void onSignFailure() {
                loginState.postValue(SIGN_IN_FAILURE);
            }
            @Override public void loginFieldIsEmpty() {

            }
            @Override public void passwordFieldIsEmpty() {

            }
        };

        fireAuth.loginUserAccount(login.getValue(), password.getValue());
    }

    private void initFireDB(String email) {
        db = new FirebaseDatabase(email) {
            @Override public void onGetTrainings(ArrayList<Training> list) {
                trainings.setValue(list);
                loadEventsForTraining(list.get(0));
            }
            @Override public void onGetExercises(ArrayList<Exercise> list) {
                exercises.setValue(list);
                for (Exercise ex:list) {
                    exerciseDictionary.put(ex.getId(), ex.getName());
                }
            }
            @Override public void onGetEvents(ArrayList<Event> list) {
                events.setValue(list);
                if (list!=null && list.size()!=0) {
                    selectEvent(0);
                    for (Event event:list) {
                        event.setName(exerciseDictionary.get(event.getExerciseId()));
                    }
                }
            }
            @Override public void onGetUserData(User user) {
            }
            @Override public void onGetLastEvent(Event event) {
                lastEvent.setValue(event);
            }
            @Override public void onGetBestEvent(Event event) {
                bestEvent.setValue(event);
            }
        };
    }


    void completeEvent() {

    }



    String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();// TODO: 22.08.2023
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
    /**Логин для отображения в форме для signUp/signIn*/
    public MutableLiveData<String> getLogin() {
        return login;
    }
    /**Логин (email) аккаунта после успешного подключения к аккаунта*/
    public MutableLiveData<String> getSignedLogin() {
        return signedLogin;
    }
    public MutableLiveData<String> getPassword() {
        return password;
    }
    public MutableLiveData<Integer> getLoginState() {
        return loginState;
    }


    public MutableLiveData<Training> getSelectedTraining() {
        return selectedTraining;
    }
    public MutableLiveData<Integer> getSelectedEvent() {
        return selectedEvent;
    }
    public MutableLiveData<Event> getLastEvent() {
        return lastEvent;
    }
    public MutableLiveData<Event> getBestEvent() {
        return bestEvent;
    }

    /**Список всех тренировок пользователя*/
    public void loadAllTrainings() {
        SLog.e("loadAllTrainings");
        db.getTrainings();
    }
    /**Список всех упражнений пользователя*/
    public void loadAllExercises() {
        db.getExercises();
    }

    /***/
    public void getUserData() {
        db.getUserData();
    }


    public void loadEventsForTraining(Training training) {
        // TODO: 10.11.2023 если уже загружена -- не загружать
        selectedTraining.setValue(training);
        db.getEvents(training.getId());
    }

    private void getLastAndBestEvent(String exerciseId) {
        Exercise exercise = getExerciseById(exerciseId);
        String lastId = exercise.getLastEventId();
        String bestId = exercise.getBestEventId();
        db.getLastEvent(lastId);
        db.getBestEvent(bestId);
    }

    public void selectEvent(int position) {
        SLog.e("selectEvent");
        Event event = events.getValue().get(position);
        getLastAndBestEvent(event.getExerciseId());
        selectedEvent.postValue(position);
    }

    public void addWorkout(WorkoutSet set) {
        Event current = events.getValue().get(selectedEvent.getValue());
        Event last = lastEvent.getValue();
        Event best = bestEvent.getValue();
        ArrayList<WorkoutSet> lastSet = null;
        ArrayList<WorkoutSet> bestSet = null;
        if (last!=null) lastSet=last.getWorkoutSetList();
        if (best!=null) bestSet=best.getWorkoutSetList();
        current.addSet(set);

        //todo пока просто записываю в event, потом сделаю, чтобы после записи в БД автоматом загружалась ИЗ БД обновленная версия event

        int rate = Utils.compareWorkouts(current.getWorkoutSetList(), lastSet, bestSet);

        current.setRate(rate);//todo обновление рейтинга ещё не сохранено в БД

        selectedEvent.setValue(selectedEvent.getValue());

        db.updateWorkoutSet(current);//сохранение в БД
    }

    /**Зарегить новый аккаунт*/
    public void signUp(String login, String password) {
        fireAuth.registerNewUser(login, password);
        db.createUser();
    }

    /**Войти в аккаунт*/
    public void signIn(String login, String password) {
        fireAuth.loginUserAccount(login, password);
        db.createUser();// TODO: 22.08.2023 для тестирования
        db.connect(login);
    }

    public void selectChecked() {
        ArrayList<Event> list = new ArrayList<>();
        for (Exercise ex: exercises.getValue()) {
            if (ex.isChecked()) list.add(new Event(ex.getId()));
        }
        Training training = new Training(signedLogin.getValue());
        db.addTraining(training, list);
    }

    public void getUncheckedEvent() {
        ArrayList<Event> list = new ArrayList<>();
        for (Exercise ex: exercises.getValue()) {
            if (!ex.isChecked()) list.add(new Event(ex.getId()));
        }

    }

    /**Получить абстрактное упражнение по id (из списка загруженных упражнений)*/
    public Exercise getExerciseById(String id) {
        for (Exercise exercise:exercises.getValue()) {
            if (exercise.getId().equals(id)) return exercise;
        }
        return null;
    }


    /**Завершение выполнения упражнения. После завершения этот ивент становится последним в
     * упражнении, также сравнивается ивент с лучшим и, если это рекорд, то ивент становится bestEvent*/
    public void completeSelectedEvent() {
        if (events.getValue()==null) return;
        if (selectedEvent.getValue()==null) return;

        Event event = events.getValue().get(selectedEvent.getValue());
        String exerciseId = event.getExerciseId();
        event.setComplete(true);

        ArrayList<WorkoutSet> last = null;
        ArrayList<WorkoutSet> best = null;
        if (lastEvent.getValue()!=null) last = lastEvent.getValue().getWorkoutSetList();
        if (bestEvent.getValue()!=null) best = bestEvent.getValue().getWorkoutSetList();

        int compareResult = Utils.compareWorkouts(event.getWorkoutSetList(), last, best);
        if (compareResult==BEST || compareResult==BEST_NEW_WEIGHT) db.updateExerciseBest(exerciseId, event.getId());
        event.setRate(compareResult);

        db.completeEvent(event);
        db.updateExerciseLast(exerciseId, event.getId());

    }
}
