package com.cs165.domefavor.domefavor;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 *
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
        Log.d("loader", "loader in background");
        return Utilities.createListForTest();
//        LatLng lng = FragmentMap.getLatLng();
//        ArrayList<TaskItem> tasks = null;
//        try {
//             tasks = (ArrayList<TaskItem>)Server.getAllTasks(lng.latitude + "", lng.longitude + "");
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        return tasks;
    }
}
