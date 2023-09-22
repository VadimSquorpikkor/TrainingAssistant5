package com.squorpikkor.trainingassistant5.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.adapter.ExerciseListAdapter;
import com.squorpikkor.trainingassistant5.entity.BaseEntity;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

public class AddWorkoutDialog extends BaseDialog {

    NumberPicker weightPicker, countPicker, timePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        initializeWithVM(R.layout.dialog_add_workout);

        weightPicker = view.findViewById(R.id.weight_picker);
        weightPicker.setMinValue(0);
        weightPicker.setMaxValue(200);

        countPicker = view.findViewById(R.id.count_picker);
        countPicker.setMinValue(0);
        countPicker.setMaxValue(100);

        view.findViewById(R.id.button_cancel).setOnClickListener(v->dismiss());
        view.findViewById(R.id.button_select).setOnClickListener(v-> {
            float weight = weightPicker.getValue();
            int count = weightPicker.getValue();
            mViewModel.addWorkout(new WorkoutSet(weight, count));
            dismiss();
        });

        return dialog;
    }

}
