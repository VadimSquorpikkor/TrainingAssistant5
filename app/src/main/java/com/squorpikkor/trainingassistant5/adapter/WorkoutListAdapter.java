package com.squorpikkor.trainingassistant5.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.entity.WorkoutSet;

import java.util.ArrayList;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.AdapterViewHolder> {

    private ArrayList<WorkoutSet> list = new ArrayList<>();



    public interface OnItemClickListener {
        void onItemClick(WorkoutSet workoutSet);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<WorkoutSet> list) {
        if (list==null) list = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        WorkoutSet set = list.get(position);
        holder.weight.setText(set.getWeight()+" кг");
        holder.count.setText(set.getCount()+ " раз");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView weight;
        private final TextView count;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            weight = itemView.findViewById(R.id.weight);
            count = itemView.findViewById(R.id.count);

            itemView.setOnClickListener(v->{
                if (listener!=null) listener.onItemClick(list.get(getAdapterPosition()));
            });
        }
    }
}
