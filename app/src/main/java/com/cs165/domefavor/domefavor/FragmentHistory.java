package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentHistory extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<List<TaskItem>>{

    private RecyclerView listview1, listview2;
    private ArrayList<TaskItem> list1, list2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String personID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        Intent intent = getActivity().getIntent();
        Bundle mbundle = intent.getExtras();
        personID = mbundle.getString("Email");
    }

    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        listview1 = (RecyclerView) view.findViewById(R.id.profile_list_1);
        listview2 = (RecyclerView) view.findViewById(R.id.profile_list_2);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipePersonList);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        listview1.setLayoutManager(new LinearLayoutManager(getActivity()));
        listview2.setLayoutManager(new LinearLayoutManager(getActivity()));

        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public void onRefresh() {
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<List<TaskItem>> onCreateLoader(int id, Bundle args) {
        return new TaskLoader(getActivity(), personID);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<TaskItem>> loader, List<TaskItem> data) {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        list1.clear();
        list2.clear();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getStatus().equals("post")) {
                list1.add(data.get(i));
            }
            else if (data.get(i).getStatus().equals("post")) {
                list2.add(data.get(i));
            }
        }
        RecyclerAdapter recyclerAdapter1 = new RecyclerAdapter(list1);
        listview1.setAdapter(recyclerAdapter1);
        RecyclerAdapter recyclerAdapter2 = new RecyclerAdapter(list2);
        listview2.setAdapter(recyclerAdapter2);
        listview1.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // do whatever
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                Bundle mbundle = new Bundle();
                mbundle.putString("ID", list1.get(position-1).getTaskID());
                mbundle.putString("PersonID", list1.get(position-1).getPersonID());
                intent.putExtras(mbundle);
                startActivity(intent);
            }
        }));
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<TaskItem>> loader) {
    }
}

class TaskLoader extends AsyncTaskLoader<List<TaskItem>> {
    public String personID;
    public TaskLoader(Context context, String personID) {
        super(context);
        this.personID = personID;
    }

    @Override
    protected void onStartLoading() {
        forceLoad(); //Force an asynchronous load.
    }

    @Override
    public List<TaskItem> loadInBackground() {
        List<TaskItem> tasks = null;
        try {
            tasks = Server.getPersonTasks(personID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
