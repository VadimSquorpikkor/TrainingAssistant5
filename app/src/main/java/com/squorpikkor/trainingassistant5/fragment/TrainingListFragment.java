package com.squorpikkor.trainingassistant5.fragment;

import static com.squorpikkor.trainingassistant5.MainViewModel.PAGE_TRAINING;

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
import com.squorpikkor.trainingassistant5.adapter.TrainingListAdapter;
import com.squorpikkor.trainingassistant5.dialog.SelectExerciseForTrainingDialog;

/**Список всех тренировок*/
public class TrainingListFragment extends Fragment {

    private MainViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(getActivity());
        View view = inflater.inflate(R.layout.fragment_training_list, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        RecyclerView recycler = view.findViewById(R.id.recycler);
        TrainingListAdapter adapter = new TrainingListAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(training -> {
            mViewModel.loadEventsForTraining(training);
            mViewModel.getSelectedPage().setValue(PAGE_TRAINING);
        });
        mViewModel.getTrainings().observe(getViewLifecycleOwner(), adapter::setList);

        view.findViewById(R.id.add_training).setOnClickListener(v->addTraining());

        return view;
    }

    private void addTraining() {
        SelectExerciseForTrainingDialog.newInstance().show(getParentFragmentManager(), null);
    }

}
