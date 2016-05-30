package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentHistory extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<List<TaskItem>>{

    private RecyclerView listview1, listview2;
    private ArrayList<TaskItem> list1, list2;
    private List<Uri> url1, url2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String personID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        url1 = new ArrayList<>();
        url2 = new ArrayList<>();
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
        getLoaderManager().restartLoader(0, null, this);
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
        url1.clear();
        url2.clear();
        System.out.println("DATA = " + data.size());
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getStatus().equals("post")) {
                list1.add(data.get(i));
            }
            else if (data.get(i).getStatus().equals("take")) {
                list2.add(data.get(i));
            }
        }
        System.out.println("list1 = " + list1.size() + ", list2 = " + list2.size());
        for (int i = 0; i  < list1.size(); i++) {
            new downloadTask().execute(list1.get(i).getUrl(), list1.get(i).getTaskID());
        }
        for (int i = 0; i < list2.size(); i++) {
            new downloadTask2().execute(list2.get(i).getUrl(), list2.get(i).getTaskID());
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<TaskItem>> loader) {
    }


    class downloadTask extends AsyncTask<String, Void, Uri> {
        @Override
        protected Uri doInBackground(String... params) {
            if (!params[0].equals("N/A")) {
                try {
                    String name = "photo" + System.currentTimeMillis();
                    FileOutputStream fos = getActivity().openFileOutput(name, Context.MODE_WORLD_READABLE);
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    if (conn.getResponseCode() == 200) {
                        System.out.println("get result!");
                        InputStream is = conn.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        is.close();
                        fos.close();
                        url1.add(Uri.fromFile(getActivity().getFileStreamPath(name)));
                        return Uri.fromFile(getActivity().getFileStreamPath(name));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            else {
                url1.add(null);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Uri uri) {
            System.out.println("SIZE ==== " + url1.size());
            if (url1.size() == list1.size() - 1) {
                System.out.println("hahahaha");
                for (int i = 0; i < url1.size(); i++) {
                    System.out.println("URL = " + url1.get(i));
                }
                RecyclerAdapter recyclerAdapter1 = new RecyclerAdapter(list1, url1);
                listview1.setAdapter(recyclerAdapter1);
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
        }
    }

    class downloadTask2 extends AsyncTask<String, Void, Uri> {
        @Override
        protected Uri doInBackground(String... params) {
            if (!params[0].equals("N/A")) {
                try {
                    String name = "photo" + System.currentTimeMillis();
                    System.out.println(getActivity().getFileStreamPath(name));
                    FileOutputStream fos = getActivity().openFileOutput(name, Context.MODE_WORLD_READABLE);
                    URL url = new URL(params[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    if (conn.getResponseCode() == 200) {
                        System.out.println("get result!");
                        InputStream is = conn.getInputStream();
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        is.close();
                        fos.close();
                        url2.add(Uri.fromFile(getActivity().getFileStreamPath(name)));
                        return Uri.fromFile(getActivity().getFileStreamPath(name));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            else {
                url2.add(null);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Uri uri) {
            if (url2.size() == list2.size()) {
                RecyclerAdapter recyclerAdapter2 = new RecyclerAdapter(list2, url2);
                listview2.setAdapter(recyclerAdapter2);
            }
        }
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
