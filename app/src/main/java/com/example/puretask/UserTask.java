package com.example.puretask;

import java.util.Timer;
import java.util.TimerTask;

public class UserTask {

    private int id;
    private String taskName;
    private String taskDesc;
    private int timeSpent;
    private Timer timer;
    private boolean running;

    public UserTask(int id, String taskName, String taskDesc,int timeSpent) {
        this.id = id;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.running = false;
        this.timeSpent = timeSpent;
    }

    //toString
    public String toString() {
        int seconds = timeSpent%60;
        int minutes = (timeSpent%3600)/60;
        int hours = timeSpent/3600;
        String taskString = "";
        taskString += this.getTaskId() + "\tName: " + this.taskName + "\n";
        taskString += "\tDescription: " + this.taskDesc;
        taskString += "\n\tTime spent on this task: " + hours + " hour(s) " + minutes + " minute(s) " + seconds + " second(s)";
        taskString += "\n\tRunning: " + this.getRunning();

        return taskString;
    }

    public void startTask() {
        if (!this.getRunning()) {
            this.running = true;
            timer = new Timer();
            //increments timeSpent
            TimerTask count = new TimerTask() {

                @Override
                public void run() {
                    setTimeSpent(timeSpent + 1);
                }
            };
            //perform count once every second
            timer.schedule(count, 1000, 1000);
        }
    }

    public void endTask() {
        if (this.running) {
            this.running = false;
            this.timer.cancel();
        }
    }

    //setters
    public void setTimeSpent(int time) { this.timeSpent = time; }
    public void setTaskName(String name) {this.taskName = name;}
    public void setDesc(String desc) {this.taskDesc = desc;}


    //getters
    public int getTaskId() { return this.id; }
    public String getTaskName() { return this.taskName; }
    public String getDesc() { return this.taskDesc; }
    public int getTimeSpent() { return this.timeSpent; }
    public boolean getRunning() {return this.running;}

} // Task
