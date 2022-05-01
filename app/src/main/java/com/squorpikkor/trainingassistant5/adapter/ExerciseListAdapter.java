package com.squorpikkor.trainingassistant5.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.entity.Exercise;
import com.squorpikkor.trainingassistant5.entity.Training;

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
        if (list==null) this.list = new ArrayList<>();
        this.list = list;
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
//        String date = training.getDate();//todo
//        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView date;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
//            date = itemView.findViewById(R.id.date);//todo
            itemView.setOnClickListener(v->{
                if (listener!=null) listener.onItemClick(list.get(getAdapterPosition()));
            });
        }
    }
}
