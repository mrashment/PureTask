package com.example.puretask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    ArrayList<UserTask> tasks;
    UserTask currentTask;
    TextView currentTimeTextView;
    Context context;

    public TaskAdapter(ArrayList<UserTask> tasks, Context context) {
        this.tasks = tasks;
        this.context = context;
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

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView,timeTextView;
        Button startTimerButton;

        public MyViewHolder(@NonNull View v) {
            super(v);

            v.setOnClickListener(this);
            nameTextView = v.findViewById(R.id.nameTextView);
            timeTextView = v.findViewById(R.id.timeTextView);
            startTimerButton = v.findViewById(R.id.startTimerButton);
            startTimerButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = this.getLayoutPosition();
            if (v instanceof Button) {
                switch (startTimerButton.getText().toString()) {
                    case "Start":
                        tasks.get(pos).startTask();
                        startTimerButton.setText("Stop");
                        break;
                    case "Stop":
                        tasks.get(pos).endTask();
                        ((MainActivity)context).writeToFile();
                        startTimerButton.setText("Start");
                        break;
                    default:
                        break;
                }
            } else {
                Intent intent = new Intent(context,TaskDetailActivity.class);
                intent.putExtra("UserTask",tasks.get(pos));
                context.startActivity(intent);
            }

        }
    }
}
