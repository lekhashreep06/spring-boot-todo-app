package com.app.todo.model;

public class Todo {
    int id;
    String task;
    Boolean isCompleted;

    public Todo(int id, String task, Boolean isCompleted) {
        this.id = id;
        this.task = task;
        this.isCompleted = isCompleted;
    }

    public Todo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
