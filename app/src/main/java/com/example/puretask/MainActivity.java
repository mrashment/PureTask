package com.example.puretask;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<UserTask> tasks;
    private final String FILE_NAME = "tasks.txt";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            // updates the times on items
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // tasks will hold all the user defined tasks
        tasks = new ArrayList<>();
        readFromFile();

        adapter = new TaskAdapter(tasks,this);
        recyclerView.setAdapter(adapter);

        // thread to update display each second
        new Thread() {
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(0);
                    }
                } catch (InterruptedException e ) {
                    Thread.currentThread().interrupt();
                }
            }

        }.start();

        // we'll use this to add new tasks
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewTask();
            }
        });
    }

    /**
     * reads data from internal storage
     */
    private void readFromFile() {
        try {
            InputStream is = openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine())!= null) {
                if (line.contains("***")) {
                    String name = reader.readLine();
                    String desc = reader.readLine();
                    int time = Integer.parseInt(reader.readLine());
                    tasks.add(new UserTask(name,desc,time));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * writes data to internal storage
     */
    private void writeToFile() {
        try {
            StringBuilder sb = new StringBuilder();

            for (UserTask t : tasks) {
                sb.append("***\n");
                sb.append(t.getTaskName()).append("\n");
                sb.append(t.getDesc()).append("\n");
                sb.append(t.getTimeSpent()).append("\n");
            }
            FileOutputStream fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            fos.write(sb.toString().getBytes());
            fos.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNewTask() {
        Intent addIntent = new Intent(this,AddTaskActivity.class);
        startActivity(addIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        writeToFile();
        super.onPause();
    }
}
