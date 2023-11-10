package com.squorpikkor.trainingassistant5;

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
    private final MutableLiveData<ArrayList<WorkoutSet>> sets;

    // TODO: 24.08.2023 мютабл не нужен. Простые классы. или вообще удалить
    //Entities
    private final MutableLiveData<Training> selectedTraining;
    private final MutableLiveData<Event> selectedEvent;
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

    private final FirebaseDatabase db;
    FireAuth fireAuth;
    @Deprecated
    HashMap<String, String> exerciseDictionary;

    public MainViewModel() {
        exerciseDictionary = new HashMap<>();
        trainings = new MutableLiveData<>(new ArrayList<>());
        exercises = new MutableLiveData<>(new ArrayList<>());
        events = new MutableLiveData<>(new ArrayList<>());
        lastEvent = new MutableLiveData<>();
        bestEvent = new MutableLiveData<>();
        sets = new MutableLiveData<>(new ArrayList<>());
        db = new FirebaseDatabase() {
            @Override public void onGetTrainings(ArrayList<Training> list) {
                SLog.e("♦onGetTrainings");
                trainings.setValue(list);
                loadEventsForTraining(list.get(0));
            }
            @Override public void onGetExercises(ArrayList<Exercise> list) {
                SLog.e("♦onGetExercises");
                exercises.setValue(list);
                for (Exercise ex:list) {
                    exerciseDictionary.put(ex.getId(), ex.getName());
                }
            }
            @Override public void onGetEvents(ArrayList<Event> list) {
                SLog.e("♦onGetEvents");
                events.setValue(list);
                for (Event event:list) {
                    event.setName(exerciseDictionary.get(event.getExerciseId()));
                }
                if (list!=null && list.size()!=0) selectEvent(list.get(0));
            }

            @Override public void onGetUserData(User user) {
                SLog.e("♦onGetUserData");
                SLog.e("user.getName() = "+user.getName());
                SLog.e("user.getEmail() = "+user.getEmail());

            }
            @Override public void onGetLastEvent(Event event) {
                SLog.e("♦onGetLastEvent");
                lastEvent.setValue(event);
            }
            @Override public void onGetBestEvent(Event event) {
                SLog.e("♦onGetBestEvent");
                bestEvent.setValue(event);
            }
        };

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
    public MutableLiveData<ArrayList<WorkoutSet>> getWorkoutSets() {
        return sets;
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
    public MutableLiveData<Event> getSelectedEvent() {
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
        db.getTrainings(signedLogin.getValue());
    }
    /**Список всех упражнений пользователя*/
    public void loadAllExercises() {
        db.getExercises(signedLogin.getValue());
    }

    /***/
    public void getUserData() {
        db.getUserData(signedLogin.getValue());
    }


    public void loadEventsForTraining(Training training) {
        // TODO: 10.11.2023 если уже загружена -- не загружать
        selectedTraining.setValue(training);
        db.getEvents(signedLogin.getValue(), training.getId());
    }

    private void getLastAndBestEvent(Event event) {
        Exercise exercise = getExerciseById(event.getExerciseId());
        String lastId = exercise.getLastEventId();
        String bestId = exercise.getBestEventId();
        db.getLastEvent(signedLogin.getValue(), lastId);
        db.getBestEvent(signedLogin.getValue(), bestId);
    }

    public void selectEvent(Event event) {
        SLog.e("selectEvent");
        getLastAndBestEvent(event);
        selectedEvent.postValue(event);
    }

    public void addWorkout(WorkoutSet set) {
        Event current = selectedEvent.getValue();
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

        selectedEvent.setValue(current);

        db.updateWorkoutSet(signedLogin.getValue(), current);//сохранение в БД
    }

    /**Зарегить новый аккаунт*/
    public void signUp(String login, String password) {
        fireAuth.registerNewUser(login, password);
        db.createUser(login);
    }

    /**Войти в аккаунт*/
    public void signIn(String login, String password) {
        fireAuth.loginUserAccount(login, password);
        db.createUser(login);// TODO: 22.08.2023 для тестирования
        db.connect(login);
    }

    public void selectChecked() {
        ArrayList<Event> list = new ArrayList<>();
        for (Exercise ex: exercises.getValue()) {
            if (ex.isChecked()) list.add(new Event(ex.getId()));
        }
        Training training = new Training(signedLogin.getValue());
        db.addTraining(training, list, signedLogin.getValue());
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


}
