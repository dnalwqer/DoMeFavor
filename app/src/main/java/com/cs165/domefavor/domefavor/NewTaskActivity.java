package com.cs165.domefavor.domefavor;

import android.app.DialogFragment;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * Created by Jilai Zhou on 5/23/2016.
 */
public class NewTaskActivity extends AppCompatActivity {
    private static final String TAG = "NTAct";

    private TaskItem mTask;
    private String mTime;
    private Calendar mCalendar;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);

        mTask = new TaskItem();
        Button editTimeBtn = (Button)findViewById(R.id.editTaskTime);
        Button postBtn = (Button) findViewById(R.id.postButton);
        Button resetBtn = (Button) findViewById(R.id.resetButton);


        editTimeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DialogFragment fragmentDate = DoMeFavorDialogFragment.newInstance(DoMeFavorDialogFragment.DIALOG_ID_DATE);
                fragmentDate.show(getFragmentManager(), "new_task_activity_tag");
                Log.d(TAG, "I'm clicked");
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                postTask();
                Log.d(TAG, "post something");
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "reset the data");
                clearTextbox();
            }
        });
    }
    private void postTask(){
        int defaultID = -1;
        Location loc = Utilities.getLocation(this);
//        mTaskItem = new TaskItem(defaultID, );
    }

    private void clearTextbox(){
        EditText nameTextBox = (EditText) findViewById(R.id.editTaskName);
        EditText detailTextBox = (EditText) findViewById(R.id.editTaskDetail);
        nameTextBox.setText("");
        detailTextBox.setText("");
    }

    public void callTimeDialog(){
        DialogFragment fragmentTime = DoMeFavorDialogFragment.newInstance(DoMeFavorDialogFragment.DIALOG_ID_TIME);
        fragmentTime.show(getFragmentManager(), "new_task_activity_tag");
    }

    public void updateDate(int year, int monthOfYear, int dayOfMonth){
        mCalendar.set(year, monthOfYear, dayOfMonth);
        callTimeDialog();
    }

    public void updateTime(int hourOfDay, int minute){
        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, 0);
    }

    private void setupTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MMM dd yyyy", Locale.US);
        mTime = sdf.format(mCalendar.getTime());
    }

}
