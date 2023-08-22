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
import com.squorpikkor.trainingassistant5.adapter.ExerciseListAdapter;

/**Бывший ExerciseListFragment
 * Фрагмент -- текущая тренировка со списком упражнений
 * Также отображается инфа выбранной тренировки: дата, сколько рекордов и т.д.
 *
 * */
public class TrainingFragment extends Fragment {

    private MainViewModel mViewModel;

    public static TrainingFragment newInstance() {
        return new TrainingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ThemeUtils.onActivityCreateSetTheme(getActivity());
        View view = inflater.inflate(R.layout.fragment_training, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        RecyclerView recycler = view.findViewById(R.id.recycler);
        EventListAdapter adapter = new EventListAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(exercise -> {
//            mViewModel.selectExercise(exercise);
//            openInfoFragment();
        });
        mViewModel.getEvents().observe(getViewLifecycleOwner(), adapter::setList);

        return view;
    }

    private void openInfoFragment() {
//        requireActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, ExerciseFragment.newInstance())
//                .addToBackStack(null)
//                .commit();
    }

}
