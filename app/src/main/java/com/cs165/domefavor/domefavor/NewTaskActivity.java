package com.cs165.domefavor.domefavor;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 * Created by Jilai Zhou on 5/23/2016.
 */
public class NewTaskActivity extends AppCompatActivity {
    private static final String TAG = "NTAct";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);

        Button editTimeBtn = (Button)findViewById(R.id.editTaskTime);
        Button postBtn = (Button) findViewById(R.id.postButton);
        Button resetBtn = (Button) findViewById(R.id.resetButton);

        editTimeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG, "I'm clicked");
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postTask();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        EditText addressTextBox = (EditText) findViewById(R.id.editTaskAddress);
        nameTextBox.setText("");
        detailTextBox.setText("");
        addressTextBox.setText("");
    }

}
