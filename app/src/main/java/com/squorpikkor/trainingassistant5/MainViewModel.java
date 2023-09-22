package com.squorpikkor.trainingassistant5;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.squorpikkor.trainingassistant5.data.FirebaseDatabase;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;
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
        sets = new MutableLiveData<>(new ArrayList<>());
        db = new FirebaseDatabase() {
            @Override public void onGetTrainings(ArrayList<Training> list) {
                trainings.setValue(list);
            }
            @Override public void onGetExercises(ArrayList<Exercise> list) {
                exercises.setValue(list);
                for (Exercise ex:list) {
                    exerciseDictionary.put(ex.getId(), ex.getName());
                }
            }
            @Override public void onGetEvents(ArrayList<Event> list) {
                events.setValue(list);
                for (Event event:list) {
                    event.setName(exerciseDictionary.get(event.getExerciseId()));
                }
            }
//            @Override public void onGetWorkouts(ArrayList<WorkoutSet> list) {
//                sets.setValue(list);
//            }
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

    /**Список всех тренировок пользователя*/
    public void loadAllTrainings() {
        db.getTrainings(signedLogin.getValue());
    }
    /**Список всех упражнений пользователя*/
    public void loadAllExercises() {
        db.getExercises(signedLogin.getValue());
    }



    public void loadEventsForTraining(Training training) {
        selectedTraining.setValue(training);

        db.getEvents(signedLogin.getValue(), training.getId());
    }
    public void selectEvent(Event event) {
        selectedEvent.postValue(event);
        ///////////////////sets.setValue(Utils.parseSetFromString(event.getWorkoutSet()));
    }

    public void addWorkout(WorkoutSet set) {
        Event event = selectedEvent.getValue();
        event.addSet(set);
        db.updateWorkoutSet(signedLogin.getValue(), event);

        //todo пока просто записываю в event, потом сделаю, чтобы после записи в БД автоматом загружалась из БД обновленная версия event
        selectedEvent.setValue(event);
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
