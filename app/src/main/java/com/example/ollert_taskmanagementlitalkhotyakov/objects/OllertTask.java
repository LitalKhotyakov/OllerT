package com.example.ollert_taskmanagementlitalkhotyakov.objects;

import java.util.Date;

public class OllertTask {
    private String task_name;
    private String task_content;
    private Date task_date;
    private boolean isDone;

    public OllertTask(String task_name, String task_content, Date task_date, boolean status) {
        this.task_name = task_name;
        this.task_content = task_content;
        this.task_date = task_date;
        this.isDone = status;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_content() {
        return task_content;
    }

    public void setTask_content(String task_content) {
        this.task_content = task_content;
    }

    public Date getTask_date() {
        return task_date;
    }

    public void setTask_date(Date task_date) {
        this.task_date = task_date;
    }

    public boolean getDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }
}
