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
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(getActivity());
        view = inflater.inflate(R.layout.fragment_training, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        RecyclerView recycler = view.findViewById(R.id.recycler);
        EventListAdapter adapter = new EventListAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(event -> {
            mViewModel.selectEvent(event);
            mViewModel.getSelectedPage().setValue(PAGE_EXERCISE);
        });
        mViewModel.getEvents().observe(getViewLifecycleOwner(), list -> {
            adapter.setList(list);
            if (list!=null && list.size()!=0) mViewModel.selectEvent(list.get(list.size()-1));

        });

        mViewModel.getSelectedTraining().observe(getViewLifecycleOwner(), this::updateTraining);

        return view;
    }

    private void updateTraining(Training training) {
        ((TextView)view.findViewById(R.id.date)).setText(training.getFormattedDate());
        ((TextView)view.findViewById(R.id.eff)).setText(training.getEffectivity());//todo

    }
}
