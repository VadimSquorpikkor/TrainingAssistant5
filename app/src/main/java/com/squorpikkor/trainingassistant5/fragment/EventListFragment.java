package com.squorpikkor.trainingassistant5.fragment;

import static com.squorpikkor.trainingassistant5.MainViewModel.PAGE_EXERCISE;

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
import com.squorpikkor.trainingassistant5.adapter.EventListAdapter;
import com.squorpikkor.trainingassistant5.entity.Training;

/**Бывший ExerciseListFragment
 * Фрагмент -- текущая тренировка со списком упражнений
 * Также отображается инфа выбранной тренировки: дата, сколько рекордов и т.д.
 * */
public class EventListFragment extends Fragment {

    private MainViewModel mViewModel;
    private TextView dateText, effText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(getActivity());
        View view = inflater.inflate(R.layout.fragment_training, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        dateText = view.findViewById(R.id.date);
        effText  = view.findViewById(R.id.eff);

        RecyclerView recycler = view.findViewById(R.id.recycler);
        EventListAdapter adapter = new EventListAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            mViewModel.selectEvent(position);
            mViewModel.getSelectedPage().setValue(PAGE_EXERCISE);
        });
        mViewModel.getEvents().observe(getViewLifecycleOwner(), adapter::setList);

        mViewModel.getSelectedTraining().observe(getViewLifecycleOwner(), this::updateTraining);

        return view;
    }

    private void updateTraining(Training training) {
        dateText.setText(training.getFormattedDate());
        effText.setText(training.getEffectivity());//todo
    }
}
