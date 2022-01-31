package com.squorpikkor.trainingassistant5;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends ViewModel {

    private final FirebaseAuth mAuth;

    public MainViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }
}
