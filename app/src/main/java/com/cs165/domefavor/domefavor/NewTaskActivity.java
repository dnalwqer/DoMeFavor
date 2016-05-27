package com.cs165.domefavor.domefavor;

import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.marvinlabs.widget.floatinglabel.edittext.FloatingLabelEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * Created by Jilai Zhou on 5/23/2016.
 */
public class NewTaskActivity extends AppCompatActivity implements FloatingLabelEditText.EditTextListener {
    private static final String TAG = "NTAct";

    private TaskItem mTask;
    private String mTime;
    private String mID, mLng, mLat;
    private Calendar mCalendar;
    private FloatingLabelEditText nameTextBox, detailTextBox, priceTextBox;
    private TextView timeText;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);

        mTask = new TaskItem();
        Intent intent = getIntent();
        Bundle mBundle = intent.getExtras();
        mID = mBundle.getString("Email");
        Button editTimeBtn = (Button)findViewById(R.id.editTaskTime);
        Button postBtn = (Button) findViewById(R.id.postButton);
        Button resetBtn = (Button) findViewById(R.id.resetButton);

        nameTextBox = (FloatingLabelEditText) findViewById(R.id.editTaskName);
        nameTextBox.setEditTextListener(this);
        detailTextBox = (FloatingLabelEditText) findViewById(R.id.editTaskDetail);
        detailTextBox.setEditTextListener(this);
        priceTextBox = (FloatingLabelEditText) findViewById(R.id.editTaskPrice);
        priceTextBox.setEditTextListener(this);

        //display current time
        timeText = (TextView)findViewById(R.id.timeView);
        mCalendar = Calendar.getInstance();
        getTimeStr();
        editTimeBtn.setBackgroundResource(R.drawable.ic_time_change);
        resetBtn.setBackgroundResource(R.drawable.selector_button_reset);
        postBtn.setBackgroundResource(R.drawable.selector_button_post);

        editTimeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DialogFragment fragmentDate = DoMeFavorDialogFragment.newInstance(DoMeFavorDialogFragment.DIALOG_ID_DATE);
                fragmentDate.show(getFragmentManager(), "new_task_activity_tag");
                Log.d(TAG, "I'm clicked");
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LatLng loc = FragmentMap.getLatLng();

                mTask.setLongitude(""+loc.longitude);
                mTask.setLatitude(""+loc.latitude);
                postTask();
                finish();
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
        mTask.setPersonID(mID);
//        Log.d(TAG, "" + nameTextBox.getInputWidgetText());
        mTask.setTaskName("" + nameTextBox.getInputWidgetText());
        mTask.setContent("" + detailTextBox.getInputWidgetText());
        if (!priceTextBox.getInputWidgetText().toString().equals(""))
            mTask.setPrice(Double.parseDouble("" + priceTextBox.getInputWidgetText()));


        new postTaskAsyncTask().execute();
    }

    private void clearTextbox(){
        nameTextBox.setInputWidgetText("");
        detailTextBox.setInputWidgetText("");
        nameTextBox.setLabelText("Task Name");
        detailTextBox.setLabelText("Task Details");
        mCalendar = Calendar.getInstance();
        getTimeStr();

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
        getTimeStr();
    }

    private void getTimeStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss MMM dd yyyy", Locale.US);
        mTime = sdf.format(mCalendar.getTime());
        mTask.setTime(mTime);
        timeText.setText(mTime);
    }

    @Override
    public void onTextChanged(FloatingLabelEditText source, String text) {
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//
    }

    private class postTaskAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Server.saveNewTask(mTask);
                Log.d(TAG, "person ID is " + mTask.getPersonID());
                Log.d(TAG, "task ID is " + mTask.getTime());
                Log.d(TAG, "task name is " + mTask.getTaskName());
                Log.d(TAG, "task content is " + mTask.getContent());
                Log.d(TAG, "task time is " + mTask.getTime());
                Log.d(TAG, "task lat is " +mTask.getLatitude());
                Log.d(TAG, "task lng is " +mTask.getLongitude());

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
