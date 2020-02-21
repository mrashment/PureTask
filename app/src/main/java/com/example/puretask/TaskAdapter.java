package com.example.puretask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    ArrayList<UserTask> tasks;

    public TaskAdapter(ArrayList<UserTask> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewHolder holder, int position) {
        UserTask task = tasks.get(position);

        holder.nameTextView.setText(task.getTaskName());
        holder.timeTextView.setText(String.valueOf(task.getTimeSpent()));
        holder.startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView,timeTextView;
        Button startTimerButton;

        public MyViewHolder(@NonNull View v) {
            super(v);

            nameTextView = v.findViewById(R.id.nameTextView);
            timeTextView = v.findViewById(R.id.timeTextView);
            startTimerButton = v.findViewById(R.id.startTimerButton);
        }

    }
}
