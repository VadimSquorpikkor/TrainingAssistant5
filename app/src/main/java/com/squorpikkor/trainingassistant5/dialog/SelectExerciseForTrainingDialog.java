package com.squorpikkor.trainingassistant5.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.adapter.ExerciseListAdapter;

public class SelectExerciseForTrainingDialog extends BaseDialog {

   public static SelectExerciseForTrainingDialog newInstance() {
      return new SelectExerciseForTrainingDialog();
   }

   @NonNull
   @Override
   public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      super.onCreateDialog(savedInstanceState);
      initializeWithVM(R.layout.dialog_select_exercise);

      mViewModel.loadExercises();

      RecyclerView recyclerView = view.findViewById(R.id.recycler);
      ExerciseListAdapter adapter = new ExerciseListAdapter();
      recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
      recyclerView.setAdapter(adapter);
      mViewModel.getExercises().observe(requireActivity(), adapter::setList);

      view.findViewById(R.id.button_cancel).setOnClickListener(v->dismiss());
      view.findViewById(R.id.button_select).setOnClickListener(v-> {
         mViewModel.selectChecked();
         dismiss();
      });

      return dialog;
   }

}


