package com.squorpikkor.trainingassistant5;

import static com.squorpikkor.trainingassistant5.App.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private MainViewModel mViewModel;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(getActivity());
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        EditText emailText = view.findViewById(R.id.editTextEmail);
        EditText passwordText = view.findViewById(R.id.editTextPassword);

        signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString());

        return view;
    }

    void signInWithEmailAndPassword(String email, String password) {
        mViewModel.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.e(TAG, "signInWithEmail:success");
//                        FirebaseUser user = mViewModel.getAuth().getCurrentUser();
//                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.getException());
//                        Toast.makeText(requireActivity(), "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
//                        updateUI(null);
                    }
                });
    }


    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            Log.e(TAG, "updateUI: NULL");
        } else {
            Log.e(TAG, "updateUI: "+currentUser.getEmail());
            Log.e(TAG, "updateUI: "+currentUser.getDisplayName());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mViewModel.getAuth().getCurrentUser();
        if(currentUser != null){
//            reload();
        }
    }

}
