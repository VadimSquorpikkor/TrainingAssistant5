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

public class MainViewModel extends ViewModel {

    public static final int PAGE_ALL_TRAININGS = 0;
    public static final int PAGE_TRAINING = 1;
    public static final int PAGE_EXERCISE = 2;

    private final MutableLiveData<ArrayList<Training>> trainings;
    private final MutableLiveData<ArrayList<Exercise>> exercises;
    private final MutableLiveData<ArrayList<Event>> events;
    private final MutableLiveData<ArrayList<WorkoutSet>> sets;

    //Entities
    private final MutableLiveData<Training> selectedTraining;
    private final MutableLiveData<Event> selectedEvent;
    private final MutableLiveData<WorkoutSet> selectedSet;

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
//    private final MutableLiveData<>

    //private final DataBaseHelper db;
    private final FirebaseDatabase db;
    FireAuth fireAuth;

    public MainViewModel() {
        trainings = new MutableLiveData<>(new ArrayList<>());
        exercises = new MutableLiveData<>(new ArrayList<>());
        events = new MutableLiveData<>(new ArrayList<>());
        sets = new MutableLiveData<>(new ArrayList<>());
//        db = new DataBaseHelper();
        db = new FirebaseDatabase();
//        db = new FirebaseDatabase() {
//            @Override public void TrainingAddComplete() {
//
//            }
//        };

        selectedTraining = new MutableLiveData<>();
        selectedEvent = new MutableLiveData<>();
        selectedSet = new MutableLiveData<>();

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
                    signedLogin.postValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());

            }
            @Override public void onRegisterFailure() {
                loginState.postValue(REGISTER_FAILURE);
            }
            @Override public void onSignInSuccess() {
                loginState.postValue(SIGN_IN_SUCCESS);
                if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    signedLogin.postValue(email);
                    getTrainings(email);
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

    /**Список всех тренировок пользователя*/
    public void getTrainings(String login) {
        db.getTrainingsByUser(login, trainings);
    }

    public void getAllExercises() {
        db.getExerciseByUser(signedLogin.getValue(), exercises);
    }



    public void getEvents(Training training) {
        //selectedPage.postValue(PAGE_TRAINING);
        selectedTraining.postValue(training);
        db.getEventByTraining(training, events);
    }

    /*public void getCurrentExercise(Event event) {
        //selectedPage.postValue(PAGE_EXERCISE);
        selectedEvent.postValue(event);
        db.getEvents(selectedTraining.getValue());
    }*/



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
//            if (ex.isChecked()) db.addEvent(new Event(ex.getId()));
        }
        /////db.addEventsList(list, events);
        Training training = new Training(signedLogin.getValue());
        db.addTraining(training, list, events);
    }
}
