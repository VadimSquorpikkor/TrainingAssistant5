package com.squorpikkor.trainingassistant5.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.entity.Exercise;

import java.util.ArrayList;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.AdapterViewHolder> {

    private ArrayList<Exercise> list;

    public interface OnItemClickListener {
        void onItemClick(Exercise exercise);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<Exercise> list) {
        if (list==null) list = new ArrayList<>();
        this.list = list;
        for (Exercise e:list) {
            Log.e("", "list - "+e.getName());
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Exercise exercise = list.get(position);
        holder.name.setText(exercise.getName());
        Log.e("", "holder - "+exercise.getName());
        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> exercise.setChecked(b));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final CheckBox checkBox;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            checkBox = itemView.findViewById(R.id.check);

            itemView.setOnClickListener(v->{
                if (listener!=null) listener.onItemClick(list.get(getAdapterPosition()));
            });
        }
    }
}
