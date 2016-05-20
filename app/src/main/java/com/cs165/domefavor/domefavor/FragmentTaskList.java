package com.cs165.domefavor.domefavor;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentTaskList extends ListFragment {
    private TaskListAdapter mTaskListAdapter;
    private View view;
    private ArrayList<TaskItem> mTaskItemList;


    public void onCreate(Bundle mBundle){
        super.onCreate(mBundle);
        mTaskListAdapter = new TaskListAdapter(getActivity());
        setListAdapter(mTaskListAdapter);
        //for testing
        mTaskItemList = Utilities.createListForTest();
    }


    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_tasklist, container, false);
        Button refreshBtn = (Button) view.findViewById(R.id.refreshButton);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Location loc = Utilities.getLocation(getActivity());
                Log.d("TaskListFrag", "lat: " + loc.getLatitude() + "; long: " + loc.getLongitude());
                try {
                    Server.getAllTasks(String.valueOf(loc.getLatitude()), String.valueOf(loc.getLongitude()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    //ListAdapter may use Weiqiang's class.
    public class TaskListAdapter extends ArrayAdapter<TaskItem> {
        public TaskListAdapter(Context context){
            super(context,android.R.layout.two_line_list_item);
        }

    }
}
