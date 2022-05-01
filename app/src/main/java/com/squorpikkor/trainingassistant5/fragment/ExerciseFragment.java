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

public class ExerciseFragment extends Fragment {

    private MainViewModel mViewModel;

    public static ExerciseFragment newInstance() {
        return new ExerciseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(getActivity());
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        return view;
    }

}
