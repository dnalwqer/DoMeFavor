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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.gcm.Task;
import com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter;

public class MyExpandableListItemAdapter extends ExpandableListItemAdapter<TaskItem> {

    private final Context mContext;

    /**
     * Creates a new ExpandableListItemAdapter with the specified list, or an empty list if
     * items == null.
     */
    public MyExpandableListItemAdapter(final Context context) {
//        super(context, R.layout.activity_expandablelistitem_card, R.id.activity_expandablelistitem_card_title, R.id.activity_expandablelistitem_card_content);
        super(context);
        mContext = context;
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
        taskNameView.setText(task.getTaskName());
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

        TaskItem task = getItem(position);

        TextView taskNameView = (TextView)tv.findViewById(R.id.activity_expandablelistitem_card_content_name);
        TextView taskTimeView = (TextView)tv.findViewById(R.id.activity_expandablelistitem_card_content_time);
        TextView taskDetailView =(TextView) tv.findViewById(R.id.activity_expandablelistitem_card_content_detail) ;
        TextView taskPriceView = (TextView) tv.findViewById(R.id.activity_expandablelistitem_card_content_price);
        TextView taskLocView = (TextView) tv.findViewById(R.id.activity_expandablelistitem_card_content_location);

        taskNameView.setText(mContext.getString(R.string.header_task_name) + "  "+task.getTaskName());
        taskTimeView.setText(mContext.getString(R.string.header_task_time) + "  "+task.getTime());
        taskDetailView.setText(mContext.getString(R.string.header_task_detail) +"  "+ task.getContent());
        taskPriceView.setText(mContext.getString(R.string.header_task_offer) +"  "+ Double.toString(task.getPrice()));
        taskLocView.setText(mContext.getString(R.string.header_locatioin) + "  "+ task.getLongitude() + " : " + task.getLatitude());

        Button bidBtn= (Button) tv.findViewById(R.id.bid);
        bidBtn.setBackgroundResource(R.drawable.ic_bid);
        bidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bid","I'm bidding number " + position);
            }
        });
        return tv;
    }

}