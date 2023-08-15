package com.squorpikkor.trainingassistant5.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squorpikkor.trainingassistant5.MainViewModel;
import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.ThemeUtils;

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

        return view;
    }

    private void openInfoFragment() {
//        requireActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, TrainingListFragment.newInstance())
//                .addToBackStack(null)
//                .commit();
    }

}
