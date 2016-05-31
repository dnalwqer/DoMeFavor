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
    private String status;
    private double price;
    private String url;
    private String biders;

    public static final String taskIDS = "taskID";
    public static final String nameS = "taskName";
    public static final String longitudeS = "longitude";
    public static final String latitudeS = "latitude";
    public static final String timeS = "time";
    public static final String contentS = "content";
    public static final String priceS = "price";
    public static final String personIDS = "personID";
    public static final String statusS = "status";
    public static final String urlS = "url";
    public static final String biderS = "biders";
    public static final String flagS = "flag";
    public static final String withCredit = "withCredit";
    public static final String withoutCredit = "withoutCredit";

    TaskItem(String id, String name, String longitude, String latitude, String tim,
             String con, double price, String personID, String status, String url){
        taskID = id;
        taskName = name;
        this.longitude = longitude;
        this.latitude = latitude;
        time = tim;
        content = con;
        this.price = price;
        this.personID = personID;
        this.status = status;
        this.url = url;
    }

    TaskItem(){
        taskID = "";
        taskName = "";
        this.longitude = "";
        this.latitude = "";
        time = "";
        content = "";
        this.price = 0;
        this.personID = "";
    }

    void setStatus(String status) { this.status = status; }

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

    void setPrice(double price){
        this.price = price;
    }

    void setUrl(String url) { this.url = url; }

    void setBiders(String biders) { this.biders = biders; }

    String getTaskID(){ return taskID; }

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

    String getStatus() { return status; }

    String getUrl() { return url; }

    double getPrice(){
        return price;
    }

    String getBiders() {
        return biders;
    }
}
