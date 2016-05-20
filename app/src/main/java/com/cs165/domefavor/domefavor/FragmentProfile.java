package com.cs165.domefavor.domefavor;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Jilai Zhou on 5/19/2016.
 */
public class FragmentProfile extends Fragment {

    private TextView name, email;
    private ListView listview1, listview2;
    private MyAdapter adapter1, adapter2;
    private ArrayList<TaskItem> list1, list2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list1 = new ArrayList<>();
        TaskItem item = new TaskItem("","buy","-70.970479","41.613032",new java.util.Date().toString(), "good", 12.3, "dart", "");
        list1.add(item);
        list2 = new ArrayList<>();
        list2.add(item);
    }

    //inflate the fragment in the UI
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Intent intent = getActivity().getIntent();
        Bundle mbundle = intent.getExtras();

        name = (TextView) view.findViewById(R.id.profile_name);
        name.setText(mbundle.getString("Name"));

        email = (TextView) view.findViewById(R.id.profile_email);
        email.setText(mbundle.getString("Email"));

        listview1 = (ListView) view.findViewById(R.id.profile_list_1);
        listview2 = (ListView) view.findViewById(R.id.profile_list_2);

        adapter1 = new MyAdapter(getActivity(), list1);
        adapter2 = new MyAdapter(getActivity(), list2);
        listview1.setAdapter(adapter1);
        listview2.setAdapter(adapter2);

        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                Bundle mbundle = new Bundle();
                mbundle.putString("ID", list2.get(position).getTaskID());
                startActivity(intent);
            }
        });
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

            textview1.setText(list.get(position).getTaskName());
            textview2.setText(list.get(position).getContent());
            return convertView;
        }
    }
}
