package com.cs165.domefavor.domefavor;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * AsyncTaskLoader for loading TaskItems in FragmentList
 * Created by Jilai Zhou on 5/23/2016.
 */
public class TaskListLoader extends AsyncTaskLoader <ArrayList<TaskItem>> {
    public TaskListLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad(); //Force an asynchronous load.
    }

    @Override
    public ArrayList<TaskItem> loadInBackground() {


        System.out.println("loadInBackground");
        LatLng loc = FragmentMap.getLatLng();
        ArrayList<TaskItem> tasks = new ArrayList<>();
        if(loc == null){
            return null;
        }
        try {
            System.out.println(loc.latitude + "   ,   " + loc.longitude);
            tasks = (ArrayList<TaskItem>)Server.getAllTasks(loc.longitude + "", loc.latitude + "");
//            tasks = (ArrayList<TaskItem>)Server.getAllTasks("-70.970479", "41.613032");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("TasksSize:"+tasks.size());
        return tasks;
    }
}
