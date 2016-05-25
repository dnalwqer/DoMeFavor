package com.cs165.domefavor.domefavor;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

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
        return Utilities.createListForTest();
    }
}
