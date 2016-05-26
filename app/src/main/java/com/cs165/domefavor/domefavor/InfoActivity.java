package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {


    private ListView listview;
    private PriceAdapter adapter;
    private List<PriceItem> list;
    private String taskID, personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        listview = (ListView) findViewById(R.id.price_list);
        list = new ArrayList<>();

        Intent intent = getIntent();
        Bundle mbundle = intent.getExtras();

        taskID = mbundle.getString("ID");
        personID = mbundle.getString("PersonID");
        new getPriceTask().execute(taskID);
    }

    class PriceAdapter extends BaseAdapter {
        public Context context;
        public List<PriceItem> list;
        private LayoutInflater layoutInflater;
        public PriceAdapter(Context context, List<PriceItem> list) {
            this.list = list;
            this.context = context;
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
                convertView = layoutInflater.inflate(R.layout.priceitem_layout, null);
            }

            TextView textView1 = (TextView) convertView.findViewById(R.id.price_list_name);
            TextView textView2 = (TextView) convertView.findViewById(R.id.price_list_email);
            TextView textView3 = (TextView) convertView.findViewById(R.id.price_list_price);

            textView1.setText("ID:" + list.get(position).getPersonID());
            textView2.setText("Age: " + list.get(position).getAge());
            textView3.setText("Price: " + list.get(position).getPrice());
            return convertView;
        }
    }

    class getPriceTask extends AsyncTask<String, Void, List<PriceItem>> {
        @Override
        protected List<PriceItem> doInBackground(String... ID) {
            List<PriceItem> items = null;
            try {
                items = Server.getAllPrice(ID[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<PriceItem> items) {
            if (items != null) {
                for (int i = 0; i < items.size(); i++) {
                    list.add(items.get(i));
                }
                adapter = new PriceAdapter(getApplicationContext(), list);
                listview.setAdapter(adapter);
                displayAdpater();
            }
        }
    }

    public void displayAdpater() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(InfoActivity.this)
                        .setTitle("Accept")
                        .setMessage("Do you want to accept this offer?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new closeTask().execute(taskID, personID);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    class closeTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... ID) {
            try {
                Server.closeOneTask(ID[0], ID[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void para) {
            Toast.makeText(getApplicationContext(), "Close the task successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
