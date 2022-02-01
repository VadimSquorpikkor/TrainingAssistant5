package com.squorpikkor.trainingassistant5;

import static com.squorpikkor.trainingassistant5.App.TAG;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private final FirebaseAuth mAuth;
    private final FireDBHelper db;

    private final MutableLiveData<String> deviceAccountEmail;
    private final MutableLiveData<Boolean> openTrainingFragment;

    @SuppressWarnings("SpellCheckingInspection")
    String userId; //7uA2VBPAvmn1SvIMCDVD

    public MainViewModel() {
        Log.e(TAG, "MainViewModel: start");
        db = new FireDBHelper();
        deviceAccountEmail = new MutableLiveData<>();
        openTrainingFragment = new MutableLiveData<>(false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //deviceAccountEmail.setValue(getUserEmail());


        if (currentUser != null) {
            Log.e(TAG, "MainViewModel: currentUser Email = " + currentUser.getEmail());
            deviceAccountEmail.setValue(currentUser.getEmail());
        } else {
            Log.e(TAG, "MainViewModel: null");
        }
    }

    public MutableLiveData<String> getDeviceAccountEmail() {
        return deviceAccountEmail;
    }
    public MutableLiveData<Boolean> getOpenTrainingFragment() {
        return openTrainingFragment;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    void getUserIdByEmail(String email) {
        db.getUserIdByEmail(email, openTrainingFragment);
    }


}
