package com.squorpikkor.trainingassistant5.adapter;

import static com.squorpikkor.trainingassistant5.App.getContext;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.trainingassistant5.App;
import com.squorpikkor.trainingassistant5.R;
import com.squorpikkor.trainingassistant5.SLog;
import com.squorpikkor.trainingassistant5.Utils;
import com.squorpikkor.trainingassistant5.entity.Event;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.AdapterViewHolder> {

    private ArrayList<Event> list;

    public interface OnItemClickListener {
        void onItemClick(int position);
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
                getContext().getString(R.string.empty_value):event.getWorkoutSet().equals("null")?
                        getContext().getString(R.string.empty_value):event.getWorkoutSet());
        int color = Utils.getRateColor(event.getRate());
        SLog.e("rate = "+event.getRate());
        SLog.e("isComplete = "+event.isComplete());
        // TODO: 21.11.2023 цвет
        //if (event.isComplete()) holder.rate.setColorFilter(App.getContext().getResources().getColor(color));
        //if (event.isComplete()) holder.rate.setColorFilter(getContext().getResources().getColor(color));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, result;
        private final ImageView rate;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            result = itemView.findViewById(R.id.result);
            rate = itemView.findViewById(R.id.rate);

            itemView.setOnClickListener(v->{
                if (listener!=null) listener.onItemClick(getAdapterPosition());
            });
        }
    }
}
