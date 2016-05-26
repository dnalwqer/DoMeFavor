package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentHistory extends Fragment {

    private ListView listview1, listview2;
    private MyAdapter adapter1, adapter2;
    private ArrayList<TaskItem> list1, list2;
    private static final int INITIAL_DELAY_MILLIS = 500;
    private MyExpandableListItemAdapter mExpandableListItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
    }

    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        listview1 = (ListView) view.findViewById(R.id.profile_list_1);
        listview2 = (ListView) view.findViewById(R.id.profile_list_2);

        new getListTask().execute();
        return view;
    }

    class MyAdapter extends BaseAdapter {
        public Context context;
        public List<TaskItem> list;
        private LayoutInflater layoutInflater;
        public MyAdapter(Context context, List<TaskItem> list) {
            this.context = context;
            this.list = list;
            layoutInflater = LayoutInflater.from(this.context);
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_layout, null);
            }

            TextView textview1 = (TextView) convertView.findViewById(R.id.profile_task_name);
            TextView textview2 = (TextView) convertView.findViewById(R.id.profile_task);

            textview1.setText(list.get(position).getTime());
            textview2.setText(list.get(position).getContent());
            return convertView;
        }
    }

    class getListTask extends AsyncTask<Void, Void, List<TaskItem>> {
        @Override
        protected List<TaskItem> doInBackground(Void... params) {
            List<TaskItem> tasks = null;
            try {
                tasks = Server.getPersonTasks();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tasks;
        }

        @Override
        protected void onPostExecute(List<TaskItem> tasks) {
            if (tasks != null) {
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).getStatus().equals("post")) {
                        list1.add(tasks.get(i));
                    }
                    else if (tasks.get(i).getStatus().equals("take")) {
                        list2.add(tasks.get(i));
                    }
                }

                mExpandableListItemAdapter = new MyExpandableListItemAdapter(getActivity(), list1);
                AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mExpandableListItemAdapter);
                alphaInAnimationAdapter.setAbsListView(listview1);

                assert alphaInAnimationAdapter.getViewAnimator() != null;
                alphaInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

                listview1.setAdapter(mExpandableListItemAdapter);

//                adapter1 = new MyAdapter(getActivity(), list1);
//                adapter2 = new MyAdapter(getActivity(), list2);
//                listview1.setAdapter(adapter1);
//                listview2.setAdapter(adapter2);

                listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), InfoActivity.class);
                        Bundle mbundle = new Bundle();
                        mbundle.putString("ID", list1.get(position).getTaskID());
                        mbundle.putString("PersonID", list1.get(position).getPersonID());
                        intent.putExtras(mbundle);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
