package com.squorpikkor.trainingassistant5.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.MainViewModel;
import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.ThemeUtils;
import com.squorpikkor.trainingassistant5.adapter.WorkoutListAdapter;
import com.squorpikkor.trainingassistant5.dialog.AddWorkoutDialog;
import com.squorpikkor.trainingassistant5.entity.Event;
import com.squorpikkor.trainingassistant5.entity.Exercise;

/**Фрагмент с отображением (Event) — текущей тренировки (не абстрактной) с отображением списка подходов*/
public class EventFragment extends Fragment {

    private MainViewModel mViewModel;
    private Exercise selectedExercise;

    private View view;

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(getActivity());
        view = inflater.inflate(R.layout.fragment_exercise, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        RecyclerView recycler = view.findViewById(R.id.recycler);
        WorkoutListAdapter adapter = new WorkoutListAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(workout -> {
//            mViewModel.selectExercise(exercise);
//            openInfoFragment();
        });
        mViewModel.getSelectedEvent().observe(getViewLifecycleOwner(), event -> {
            updateFragment(event);
            adapter.setList(event.getWorkoutSetList());
        });


        RecyclerView lastRecycler = view.findViewById(R.id.last_recycler);
        WorkoutListAdapter adapter1 = new WorkoutListAdapter();
        lastRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        lastRecycler.setAdapter(adapter1);
        mViewModel.getLastEvent().observe(getViewLifecycleOwner(), event ->
                adapter1.setList(event.getWorkoutSetList()));

        RecyclerView bestRecycler = view.findViewById(R.id.best_recycler);
        WorkoutListAdapter adapter2 = new WorkoutListAdapter();
        bestRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        bestRecycler.setAdapter(adapter2);
        mViewModel.getBestEvent().observe(getViewLifecycleOwner(), event ->
                adapter2.setList(event.getWorkoutSetList()));


        view.findViewById(R.id.add_set).setOnClickListener(view1 -> {
            new AddWorkoutDialog().show(getParentFragmentManager(), null);
        });

        return view;
    }

    public void updateFragment(Event event) {
        selectedExercise = mViewModel.getExerciseById(event.getExerciseId());
        ((TextView)view.findViewById(R.id.event_name)).setText(selectedExercise.getName());//todo выбирать имя тренировки при сеттинге selectedEvent
        ((TextView)view.findViewById(R.id.rate)).setText(""+event.getRate());
    }

}
