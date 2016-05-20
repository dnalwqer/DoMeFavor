package com.cs165.domefavor.domefavor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {


    private ListView listview;
    private PriceAdapter adapter;
    private List<PriceItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        listview = (ListView) findViewById(R.id.price_list);
        list = new ArrayList<>();

        adapter = new PriceAdapter(this, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Accept")
                        .setMessage("Do you want to accept this offer?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                finish();
            }
        });
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

            TextView textView1 = (TextView) findViewById(R.id.price_list_name);
            TextView textView2 = (TextView) findViewById(R.id.price_list_email);
            TextView textView3 = (TextView) findViewById(R.id.price_list_price);

            textView1.setText("ID:" + list.get(position).getPersonID());
            textView2.setText("Age: " + list.get(position).getAge());
            textView3.setText("Price" + list.get(position).getPrice());
            return convertView;
        }
    }
}
