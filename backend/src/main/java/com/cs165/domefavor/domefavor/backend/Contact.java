package com.cs165.domefavor.domefavor.backend;

/**
 * Created by Alex on 16/4/17.
 */
public class Contact {
    public static final String CONTACT_PARENT_ENTITY_NAME = "ContactParent";
    public static final String CONTACT_PARENT_KEY_NAME = "ContactParent";

    public static final String CONTACT_ENTITY_NAME = "Contact";
    public static final String FIELD_NAME_taskName = "taskName";
    public static final String FIELD_NAME_lng = "lng";
    public static final String FIELD_NAME_lat = "lat";
    public static final String FIELD_NAME_time = "time";
    public static final String FIELD_NAME_content = "content";
    public static final String FIELD_NAME_price = "price";
    public static final String FIELD_NAME_id = "id";
    public static final String FIELD_NAME_poster = "poster";
    public static final String KEY_NAME = FIELD_NAME_id;
    public static final String FIELD_NAME_start = "start_time"; //@han
    public static final String FIELD_NAME_status = "status"; //@han
    public static final String status_tobefinish = "ToBeFinish"; //@han
    public static final String status_tobebid = "ToBeBid"; //@han
    public static final String FIELD_NAME_taker = "taker"; //@han

    public String taskName;
    public String lng;
    public String lat;
    public String time;
    public String content;
    public String price;
    public String id;
    public String poster;
    public long startTime = 0;   //@han
    public String status;
    public String taker; //@han

    public Contact(String taskName, String lat, String lng, String time, String content, String price,
                   String poster) {
        this.taskName = taskName;
        this.content = content;
        this.time = time;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
        this.poster = poster;
        id = System.currentTimeMillis() + taskName;
        this.status = status_tobebid;
        taker = "";
    }

    public Contact(String taskid, String taskName, String lat, String lng, String time, String content, String price,
                   String poster) {
        this.taskName = taskName;
        this.content = content;
        this.time = time;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
        this.poster = poster;
        id = taskid;
        this.status = status_tobebid;
        taker = "";
    }

    public Contact(String taskid, String taskName, String lat, String lng, String time, String content, String price,
                   String poster, String status, String taker) {
        this.taskName = taskName;
        this.content = content;
        this.time = time;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
        this.poster = poster;
        id = taskid;
        this.status = status;
        this.taker = taker;
    }

}

