package com.cs165.domefavor.domefavor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.lib.PinWheelDialog;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.util.ArrayList;

/**
 * FragmentList Class.
 * This class display all the task near your location. And allow user to bid other people's task.
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentTaskList extends ListFragment implements SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<ArrayList<TaskItem>>{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
//    private TaskListAdapter mTaskListAdapter;
    private View view;
    private FloatingActionButton mFAB;
    private static ArrayList<TaskItem> mTaskItemList;
    private MyExpandableListItemAdapter mExpandableListItemAdapter;
    private LatLng mLocation;
    private Fragment currMapFragment;
    private String mID;
    private static final int INITIAL_DELAY_MILLIS = 500;
    private PinWheelDialog pin;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        Bundle mBundle = intent.getExtras();
        mID = mBundle.getString("Email");
    }


    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_tasklist, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeTaskList);
        pin = new PinWheelDialog(getActivity());
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mListView = (ListView) view.findViewById(android.R.id.list);
//        mTaskListAdapter = new TaskListAdapter(getActivity());
        mExpandableListItemAdapter = new MyExpandableListItemAdapter(getActivity(),mID);

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mExpandableListItemAdapter);
        alphaInAnimationAdapter.setAbsListView(mListView);
        assert alphaInAnimationAdapter.getViewAnimator() != null;
        alphaInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        mListView.setAdapter(mExpandableListItemAdapter);
        mListView.setDivider(null);
        mListView.setDividerHeight(0);;

        currMapFragment =((MainActivity_v2) getActivity()).getFragment(2);
//        Log.d("loc", ""+mLocation.latitude +" : " +mLocation.longitude);
//        getLoaderManager().initLoader(0, null, this);
        return view;

    }

    /**
     * reload the data
     */
    public void refreshData(){

        pin.show();
        if (getLoaderManager().getLoader(0)==null)
            getLoaderManager().initLoader(0, null, this);
        else getLoaderManager().restartLoader(0,null,this);

    }

    /**
     * call back method of swiperefreshlayout
     */
    @Override
    public void onRefresh() {
        Log.d("FragTaskList", "I'm on refresh");

//        mLocation = ((FragmentMap)currMapFragment).getLatLng();
//        refreshData(mLocation);
        refreshData();

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
        if(data == null)
            Toast.makeText(getContext(), "Cannot Get Location", Toast.LENGTH_SHORT).show();
        else{
            mExpandableListItemAdapter.clear();
            mTaskItemList = data;
            pin.dismiss();
            mExpandableListItemAdapter.addAll(data);
            mExpandableListItemAdapter.notifyDataSetChanged();
            ((FragmentMap) currMapFragment).addMarker(mTaskItemList);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<TaskItem>> loader) {
        mExpandableListItemAdapter.clear();
    }
}
