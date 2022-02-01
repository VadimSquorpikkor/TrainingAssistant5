package com.squorpikkor.trainingassistant5;

import static com.squorpikkor.trainingassistant5.App.TAG;
import static com.squorpikkor.trainingassistant5.Constants.TABLE_USERS;
import static com.squorpikkor.trainingassistant5.Constants.USERS_EMAIL;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

class FireDBHelper {

    private final FirebaseFirestore db;

    public FireDBHelper() {
        db = FirebaseFirestore.getInstance();
    }

    void getUserIdByEmail(String email, MutableLiveData<Boolean> openTrainingFragment) {
        Log.e(TAG, "getUserIdByEmail: "+email);
        if (email.equals("")) return;
        db.collection(TABLE_USERS).whereEqualTo(USERS_EMAIL, email).get().addOnCompleteListener(task -> {
            Log.e(TAG, "getUserIdByEmail: 2");
            if (task.isSuccessful()) {
                Log.e(TAG, "getUserIdByEmail: 3");
                if (task.getResult()==null) return;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.e(TAG, "docId = "+document.getId());
                }
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
            }

        });
    }

}
