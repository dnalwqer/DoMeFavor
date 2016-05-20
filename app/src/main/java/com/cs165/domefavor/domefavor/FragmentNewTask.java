package com.cs165.domefavor.domefavor;

import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentNewTask extends Fragment {
    public static final String TAG = "fragNewTask";
    private View view;
    private TaskItem mTaskItem;

    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_newtask, container, false);

        Button editTimeBtn = (Button)view.findViewById(R.id.editTaskTime);
        Button postBtn = (Button) view.findViewById(R.id.postButton);
        Button resetBtn = (Button) view.findViewById(R.id.resetButton);
        editTimeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "I'm clicked");
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                postTask();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                clearTextbox();
            }
        });

        return view;
    }

    private void postTask(){
        int defaultID = -1;
        Location loc = Utilities.getLocation(getActivity());
//        mTaskItem = new TaskItem(defaultID, );
    }

    private void clearTextbox(){
        EditText nameTextBox = (EditText) view.findViewById(R.id.editTaskName);
        EditText detailTextBox = (EditText) view.findViewById(R.id.editTaskDetail);
        EditText addressTextBox = (EditText) view.findViewById(R.id.editTaskAddress);
        nameTextBox.setText("");
        detailTextBox.setText("");
        addressTextBox.setText("");
    }

}
