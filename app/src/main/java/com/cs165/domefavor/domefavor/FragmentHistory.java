package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.lib.PinWheelDialog;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Fragment for showing the history
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentHistory extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<List<TaskItem>>{

    private RecyclerView listview1, listview2;
    private ArrayList<TaskItem> list1, list2;
    private List<Uri> url1, url2;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String personID;
    private PinWheelDialog pin;

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
        pin = new PinWheelDialog(getActivity());

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipePersonList);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        listview1.setLayoutManager(new LinearLayoutManager(getActivity()));
        listview2.setLayoutManager(new LinearLayoutManager(getActivity()));

        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public void onRefresh() {
        pin.show();
        getLoaderManager().restartLoader(0, null, this);
    }

    public void refresh() {
        System.out.println("before");
        if (getLoaderManager().getLoader(0)== null) {
            System.out.println("wawawaw");
        }
        getLoaderManager().restartLoader(0, null, this);
        System.out.println("hahahah");
    }

    @Override
    public Loader<List<TaskItem>> onCreateLoader(int id, Bundle args) {
        return new TaskLoader(getActivity(), personID);
    }

    /**
     * Called when the data is loaded
     */
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
            }else{  //@han
                list1.add(data.get(i));
            }
        }

        if (list1.size() != 0) {
            for (int i = 0; i < list1.size(); i++) {
                new downloadTask().execute(list1.get(i).getUrl(), list1.get(i).getTaskID());
            }
        }
        else {
            pin.dismiss();
            RecyclerAdapter recyclerAdapter1 = new RecyclerAdapter(list1, url1);
            listview1.setAdapter(recyclerAdapter1);
        }
        if (list2.size() != 0) {
            for (int i = 0; i < list2.size(); i++) {
                new downloadTask2().execute(list2.get(i).getUrl(), list2.get(i).getTaskID());
            }
        }
        else {
            pin.dismiss();
            RecyclerAdapter2 recyclerAdapter2 = new RecyclerAdapter2(list2, url2);
            listview2.setAdapter(recyclerAdapter2);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<TaskItem>> loader) {
    }


    /**
     *   define a task to download the image icon
     */
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
            if (url1.size() == list1.size()) {
                pin.dismiss();
                RecyclerAdapter recyclerAdapter1 = new RecyclerAdapter(list1, url1);
                recyclerAdapter1.setOnItemClickListener(new RecyclerAdapter.ClickListener() {
                    @Override
                    public void onItemClick(int position, View view) {
                        final int Pos = position;
                        // do whatever
                        if (list1.get(position - 1).getStatus().equals("post")) {
                            Intent intent = new Intent(getActivity(), InfoActivity.class);
                            Bundle mbundle = new Bundle();
                            mbundle.putString("ID", list1.get(position - 1).getTaskID());
                            mbundle.putString("PersonID", list1.get(position - 1).getPersonID());
                            mbundle.putString("Content", list1.get(position - 1).getContent());
                            mbundle.putString("TaskName", list1.get(position - 1).getTaskName());
                            mbundle.putString("Time", list1.get(position - 1).getTime());
                            intent.putExtras(mbundle);
                            startActivity(intent);
                        } else {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Feedback")
                                    .setMessage("Do you want to give credit to the following person: " + list1.get(position - 1).getBiders())
                                    .setNegativeButton("No credit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            new closeTask().execute(list1.get(Pos - 1).getTaskID(),
                                                    list1.get(Pos - 1).getBiders(), TaskItem.withoutCredit);
                                        }
                                    })
                                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setPositiveButton("Give credit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            new closeTask().execute(list1.get(Pos - 1).getTaskID(),
                                                    list1.get(Pos - 1).getBiders(), TaskItem.withCredit);
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onItemLongClick(int position, View v) {
                        final int pos = position;
                        final NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(getActivity());

                        dialogBuilder
                                .withTitle("Close the task")                                  //.withTitle(null)  no title
                                .withTitleColor("#FFFFFF")                                  //def
                                .withDividerColor("#11000000")                              //def
                                .withMessage("Are you sure to delete the task?")                     //.withMessage(null)  no Msg
                                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                                .withDialogColor("#727272")                               //def  | withDialogColor(int resid)
                                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                                .withDuration(700)                                          //def
                                .withEffect(Effectstype.RotateBottom)                                         //def Effectstype.Slidetop
                                .withButton1Text("OK")                                      //def gone
                                .withButton2Text("Cancel")
                                .setButton1Click(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        new closeTask().execute(list1.get(pos-1).getTaskID(), personID, TaskItem.withoutCredit);
                                        dialogBuilder.dismiss();
                                    }
                                })
                                .setButton2Click(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogBuilder.dismiss();
                                    }
                                })
                                .show();
                    }
                });
                listview1.setAdapter(recyclerAdapter1);
            }
        }
    }

    class closeTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... ID) {
            try {
                Server.closeOneTask(ID[0], ID[1], ID[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void para) {
            Toast.makeText(getActivity(), "Delete the task successfully", Toast.LENGTH_SHORT).show();
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
                RecyclerAdapter2 recyclerAdapter2 = new RecyclerAdapter2(list2, url2);
                listview2.setAdapter(recyclerAdapter2);
            }
        }
    }
}

/**
 * define a loader to get the task list related to you
 */
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

