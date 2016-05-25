package com.cs165.domefavor.domefavor;

import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.Loader;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import java.util.ArrayList;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentTaskList extends ListFragment implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<ArrayList<TaskItem>>{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private TaskListAdapter mTaskListAdapter;
    private View view;
    private FloatingActionButton mFAB;
    private static ArrayList<TaskItem> mTaskItemList;


    public void onCreate(Bundle mBundle){
        super.onCreate(mBundle);
    }


    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_tasklist, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeTaskList);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mListView = (ListView) view.findViewById(android.R.id.list);
        mTaskListAdapter = new TaskListAdapter(getActivity());
        mListView.setAdapter(mTaskListAdapter);

//        mFAB = (FloatingActionButton)view.findViewById(R.id.fab);
//        setupFAB();
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    public static ArrayList<TaskItem> getAllTask (){
        return mTaskItemList;
    }

    @Override
    public void onRefresh() {
        Log.d("FragTaskList", "I'm on refresh");
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<TaskItem>> onCreateLoader(int id, Bundle args) {
        return new TaskListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<TaskItem>> loader, ArrayList<TaskItem> data) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mTaskListAdapter.clear();
        mTaskItemList = data;
        mTaskListAdapter.addAll(data);
        mTaskListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<TaskItem>> loader) {
        mTaskListAdapter.clear();
    }

//    private void setupFAB(){
//        ImageView iconSortName = new ImageView(getActivity());
//        iconSortName.setImageResource(R.drawable.ic_action_alphabets);
//
//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
//        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));
//
//        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
//        buttonSortName.setTag("TEST");
//        buttonSortName.setOnClickListener(this);
//
//        FloatingActionMenu mFABMenu = new FloatingActionMenu.Builder(getActivity())
//                .addSubActionView(buttonSortName)
//                .attachTo(mFAB)
//                .build();
//
//
//    }


    //ListAdapter may use Weiqiang's class.
    public class TaskListAdapter extends ArrayAdapter<TaskItem> {
        public TaskListAdapter(Context context){
            super(context,android.R.layout.two_line_list_item);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View listItemView = convertView;
            if (convertView==null)
                listItemView = inflater.inflate(R.layout.tasklist_single_item, null);

            TextView lineOneView = (TextView)listItemView.findViewById(R.id.textview1);
            TextView lineTwoView = (TextView)listItemView.findViewById(R.id.textview2);

            TaskItem task = getItem(position);

            lineOneView.setText(task.getTaskName());
            lineTwoView.setText(task.getTime());

            return listItemView;
        }

    }
}
