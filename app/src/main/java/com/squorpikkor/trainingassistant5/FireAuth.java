package com.squorpikkor.trainingassistant5;

import com.google.firebase.auth.FirebaseAuth;

public abstract class FireAuth {

    public void registerNewUser(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (email == null || email.equals("")) {
            loginFieldIsEmpty();
            return;
        }
        if (password == null || password.equals("")) {
            passwordFieldIsEmpty();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) onRegisterInSuccess();
                    else onRegisterFailure();
                });
    }

    public void loginUserAccount(String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (email == null || email.equals("")) {
            loginFieldIsEmpty();
            return;
        }
        if (password == null || password.equals("")) {
            passwordFieldIsEmpty();
            return;
        }
        // signIn existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) onSignInSuccess();
                            else onSignFailure();
                        });
    }

    public abstract void onRegisterInSuccess();
    public abstract void onRegisterFailure();
    public abstract void onSignInSuccess();
    public abstract void onSignFailure();
    public abstract void loginFieldIsEmpty();
    public abstract void passwordFieldIsEmpty();
}
