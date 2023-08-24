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
import com.squorpikkor.trainingassistant5.adapter.EventListAdapter;
import com.squorpikkor.trainingassistant5.adapter.WorkoutListAdapter;

/**По-сути — EventFragment, т.е. фрагмент с отображением текущей тренировки (не абстрактной) с отображением списка подходов*/
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

        RecyclerView recycler = view.findViewById(R.id.recycler);
        WorkoutListAdapter adapter = new WorkoutListAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(exercise -> {
//            mViewModel.selectExercise(exercise);
//            openInfoFragment();
        });
        mViewModel.getWorkoutSets().observe(getViewLifecycleOwner(), adapter::setList);

        return view;
    }

}
