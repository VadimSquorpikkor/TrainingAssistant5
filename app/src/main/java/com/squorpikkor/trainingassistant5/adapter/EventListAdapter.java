package com.squorpikkor.trainingassistant5.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.App;
import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.SLog;
import com.squorpikkor.trainingassistant5.entity.Event;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.AdapterViewHolder> {

    private ArrayList<Event> list;

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(ArrayList<Event> list) {
        if (list==null) list = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Event event = list.get(position);

        SLog.line();
        SLog.e("name = "+event.getName());
        SLog.e("result = "+event.getWorkoutSet());
        holder.name.setText(event.getName());
        holder.result.setText(event.getWorkoutSet()==null?
                App.getContext().getString(R.string.empty_value):event.getWorkoutSet().equals("null")?
                        App.getContext().getString(R.string.empty_value):event.getWorkoutSet());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, result;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            result = itemView.findViewById(R.id.result);

            itemView.setOnClickListener(v->{
                if (listener!=null) listener.onItemClick(list.get(getAdapterPosition()));
            });
        }
    }
}
