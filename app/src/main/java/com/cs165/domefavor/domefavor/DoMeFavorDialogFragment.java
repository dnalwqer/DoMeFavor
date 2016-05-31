package com.cs165.domefavor.domefavor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * DoMeFavorDialogFragment
 * This class help building dialog of date picker and time picker.
 * Created by Jilai Zhou on 5/23/2016.
 */
public class DoMeFavorDialogFragment extends DialogFragment{

    public static final String DIALOG_ID_KEY = "dialog_id";
    public static final int DIALOG_ID_DATE = 0;
    public static final int DIALOG_ID_TIME = 1;
    public static final int DIALOG_ID_BID = 2;

    public static DoMeFavorDialogFragment newInstance(int id){
        DoMeFavorDialogFragment frag = new DoMeFavorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ID_KEY, id);
        frag.setArguments(args);
        return frag;
    }

    public Dialog onCreateDialog(Bundle saveInstanceState){
        int dialog_id = getArguments().getInt(DIALOG_ID_KEY);
        final Activity parent= getActivity();
        AlertDialog.Builder builder;
        final Calendar mDateAndTime;
        final int hour, min, year, month, day;
        final EditText edittext = new EditText(parent);

        switch (dialog_id){
            case DIALOG_ID_DATE:
                mDateAndTime=Calendar.getInstance();
                year = mDateAndTime.get(Calendar.YEAR);
                month = mDateAndTime.get(Calendar.MONTH);
                day = mDateAndTime.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ((NewTaskActivity)getActivity()).updateDate(year, monthOfYear, dayOfMonth);

                    }
                };
                return new DatePickerDialog(parent, mDateListener,year,month,day);

            case DIALOG_ID_TIME:
                mDateAndTime= Calendar.getInstance();
                hour = mDateAndTime.get(Calendar.HOUR_OF_DAY);
                min = mDateAndTime.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        ((NewTaskActivity)getActivity()).updateTime(hourOfDay, minute);
                    }
                };
                return new TimePickerDialog(parent, mTimeListener, hour, min, false);

            default:
                return null;
        }
    }
}
