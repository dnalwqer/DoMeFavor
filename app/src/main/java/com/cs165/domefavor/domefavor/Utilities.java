package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.util.ArrayList;

/**
 * Created by Jilai Zhou on 5/20/2016.
 */
public class Utilities {

    public static Location getLocation(Context context){
        String svcName = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager)context.getSystemService(svcName);
        Location mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return mLocation;
    }

    public static ArrayList<TaskItem> createListForTest(){
        ArrayList<TaskItem> list = new ArrayList<TaskItem>();
//        list.add(new TaskItem("1", "walk dog", "12.543", "32.544", "May 19 2016", "Please come here soon", 12, "MJ"));
//        list.add(new TaskItem("3", "clean kitchen", "13.432", "32.14", "May 21 2016", "I'm very rich", 122, "YuShen"));
        return list;
    }
}

