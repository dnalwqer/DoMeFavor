package com.cs165.domefavor.domefavor;

/**
 * Created by xuehanyu on 5/19/16.
 */
public class TaskItem {
    private String taskID;
    private String taskName;
    private String location;
    private String time;
    private String content;
    private int price;

    TaskItem(String id, String name, String loc, String tim, String con, int price){
        taskID = id;
        taskName = name;
        location = loc;
        time = tim;
        content = con;
        this.price = price;
    }

    void setTaskID(String taskID){
        this.taskID = taskID;
    }

    void setTaskName(String taskName){
        this.taskName = taskName;
    }

    void setLocation(String location){
        this.location = location;
    }

    void setTime(String time){
        this.time = time;
    }

    void setContent(String content){
        this.content = content;
    }

    void setPrice(int price){
        this.price = price;
    }

    String getTaskID(){
        return taskID;
    }

    String getTaskName(){
        return taskName;
    }

    String getLocation(){
        return location;
    }

    String getTime(){
        return time;
    }

    int getPrice(){
        return price;
    }
}
