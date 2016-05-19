package com.cs165.domefavor.domefavor;

/**
 *
 * Created by xuehanyu on 5/19/16.
 */
public class TaskItem {
    private String taskID;
    private String taskName;
    private String longitude;
    private String latitude;
    private String time;
    private String content;
    private String personID;
    private int price;

    public static String IDS = "taskID";
    public static String nameS = "taskName";
    public static String longitudeS = "longitude";
    public static String latitudeS = "latitude";
    public static String timeS = "time";
    public static String contentS = "content";
    public static String priceS = "price";
    public static String personIDS = "personID";

    TaskItem(String id, String name, String longitude, String latitude, String tim, String con, int price, String personID){
        taskID = id;
        taskName = name;
        this.longitude = longitude;
        this.latitude = latitude;
        time = tim;
        content = con;
        this.price = price;
        this.personID = personID;
    }

    void setPersonID(String personID){
        this.personID = personID;
    }

    void setTaskID(String taskID){
        this.taskID = taskID;
    }

    void setTaskName(String taskName){
        this.taskName = taskName;
    }

    void setLongitude(String longitude){
        this.longitude = longitude;
    }

    void setLatitude(String latitude){
        this.latitude = latitude;
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

    String getLongitude(){
        return longitude;
    }

    String getLatitude(){
        return latitude;
    }

    String getTime(){
        return time;
    }

    String getContent(){
        return content;
    }

    String getPersonID(){
        return personID;
    }

    int getPrice(){
        return price;
    }
}
