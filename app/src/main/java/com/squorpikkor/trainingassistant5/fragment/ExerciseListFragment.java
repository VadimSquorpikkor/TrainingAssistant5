package com.squorpikkor.trainingassistant5.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.MainViewModel;
import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.ThemeUtils;
import com.squorpikkor.trainingassistant5.adapter.ExerciseListAdapter;

public class ExerciseListFragment extends Fragment {

    private MainViewModel mViewModel;

    public static ExerciseListFragment newInstance() {
        return new ExerciseListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(getActivity());
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        RecyclerView recycler = view.findViewById(R.id.recycler);
        ExerciseListAdapter adapter = new ExerciseListAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(exercise -> {
            mViewModel.selectExercise(exercise);
            openInfoFragment();
        });
        mViewModel.getSelectedExercise().observe(getViewLifecycleOwner(), adapter::setList);

        return view;
    }

    private void openInfoFragment() {
//        requireActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, ExerciseFragment.newInstance())
//                .addToBackStack(null)
//                .commit();
    }

}
