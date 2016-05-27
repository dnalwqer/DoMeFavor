/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cs165.domefavor.domefavor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter;

public class MyExpandableListItemAdapter extends ExpandableListItemAdapter<TaskItem> {

    private final Context mContext;
    private String mID;
    /**
     * Creates a new ExpandableListItemAdapter with the specified list, or an empty list if
     * items == null.
     */
    public MyExpandableListItemAdapter(final Context context, String ID) {
//        super(context, R.layout.activity_expandablelistitem_card, R.id.activity_expandablelistitem_card_title, R.id.activity_expandablelistitem_card_content);
        super(context);
        mContext = context;
        mID = ID;
    }

    @NonNull
    @Override
    public View getTitleView(final int position, final View convertView, @NonNull final ViewGroup parent) {
//        View tv = convertView;
//        if (tv == null) {
//            tv = new View(mContext);
//        }
        LayoutInflater inflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tv = convertView;
        if (convertView==null)
            tv = inflater.inflate(R.layout.activity_expandablelistitem_card, null);

        TaskItem task = getItem(position);
        TextView taskNameView = (TextView)tv.findViewById(R.id.activity_expandablelistitem_card_title_name);
        TextView taskTimeView = (TextView)tv.findViewById(R.id.activity_expandablelistitem_card_title_time);
//        Log.d("strLeng", ""+task.getTaskName().toString().length());
        if (task.getTaskName().length()>24) {
            String str = task.getTaskName().substring(0, 23) + "...";
            taskNameView.setText(str);
        }
        else {
            taskNameView.setText(task.getTaskName());
        }
        taskTimeView.setText(task.getTime());
        return tv;
    }

    @NonNull
    @Override
    public View getContentView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View tv =  convertView;
        LayoutInflater inflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (tv == null) {
            tv = inflater.inflate(R.layout.activity_expandeditem_card, null);
        }

        final TaskItem task = getItem(position);

        TextView taskNameView = (TextView)tv.findViewById(R.id.activity_expandablelistitem_card_content_name);
        TextView taskTimeView = (TextView)tv.findViewById(R.id.activity_expandablelistitem_card_content_time);
        TextView taskDetailView =(TextView) tv.findViewById(R.id.activity_expandablelistitem_card_content_detail) ;
        TextView taskPriceView = (TextView) tv.findViewById(R.id.activity_expandablelistitem_card_content_price);
        TextView taskLocView = (TextView) tv.findViewById(R.id.activity_expandablelistitem_card_content_location);

        taskNameView.setText(mContext.getString(R.string.header_task_name) + "  "+task.getTaskName());
        taskTimeView.setText(mContext.getString(R.string.header_task_time) + "  "+task.getTime());
        taskDetailView.setText(mContext.getString(R.string.header_task_detail) +"  "+ task.getContent());
        taskPriceView.setText(mContext.getString(R.string.header_task_offer) +"  "+ Double.toString(task.getPrice()));
        Double dis = 0.0;
        try {
            LatLng mLoc = FragmentMap.getLatLng();
            dis = distance(mLoc.longitude, mLoc.latitude, Double.parseDouble(task.getLongitude()), Double.parseDouble(task.getLatitude()));
        }catch (Exception e){
            e.printStackTrace();
        }
        taskLocView.setText(mContext.getString(R.string.header_locatioin) + "  "+  String.format( "%.2f", dis/1000));

        Button bidBtn= (Button) tv.findViewById(R.id.bid);
        bidBtn.setBackgroundResource(R.drawable.ic_bid);
        bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bid", "I'm bidding number " + position);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                Setup_Dialog_Bid(dialogBuilder, mContext, task).create().show();
//                if (bidPrice != -1){
//                    new bidAsyncTask().execute(task.getTaskID());
//                }
            }
        });
        return tv;
    }

    private AlertDialog.Builder Setup_Dialog_Bid(AlertDialog.Builder builder, Context context, final TaskItem task){
        final EditText edittext = new EditText(context);
        edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setTitle(R.string.dialog_bid_title);
        builder.setView(edittext);

        //setup positive and negative buttons and add listeners
        DialogInterface.OnClickListener PositiveButtonListener = new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int whichButton) {
                String bidPriceStr = edittext.getText().toString();
                String[] params = {bidPriceStr, task.getTaskID()};
                if (!bidPriceStr.equals("")) {
//                    Log.d("Bid", bidPriceStr);
//                    Log.d("Bid", "bid price is " + Double.parseDouble(bidPriceStr));
                    new bidAsyncTask().execute(params);
                }
            }
        };
        DialogInterface.OnClickListener NegativeButtonListener = new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int whichButton) {
                edittext.setText("");
            }
        };
        builder.setPositiveButton(R.string.dialog_button_ok, PositiveButtonListener);
        builder.setNegativeButton(R.string.dialog_button_cancel, NegativeButtonListener);
        return builder;
    }

    public class bidAsyncTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            Log.d("BidAsyncTask", params[0] + " : " + params[1]);
            try{
                Server.changePrice(Double.parseDouble(params[0]), params[1] ,mID);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(mContext, "You have bid the task!", Toast.LENGTH_SHORT).show();
        }

    }

    public double distance(double long1, double lat1, double long2,
                                  double lat2) {
        double a, b, R;
        R = 6378137; // earth radius
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }
}