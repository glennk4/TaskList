package com.glendall.tasklist;

public class Task {
    public int id;
    public String name;
    public String description;
    public String dueDate;
    public boolean completed;
    public String doneDate;



    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getDueDate(){
        return dueDate;
    }
    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }

    public boolean getCompleted(){
        return completed;
    }
    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public String getDoneDate(){
        return doneDate;
    }
    public void setDoneDate(String doneDate){
        this.doneDate = doneDate;
    }

}
