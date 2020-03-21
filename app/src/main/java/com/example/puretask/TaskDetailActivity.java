package com.example.puretask;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TaskDetailActivity extends AppCompatActivity {

    TextView nameTextView,descriptionTextView;
    UserTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        try {
            task = (UserTask)getIntent().getSerializableExtra("UserTask");
        nameTextView.setText(task.getTaskName());
        descriptionTextView.setText(task.getDesc());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
