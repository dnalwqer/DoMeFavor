package com.cs165.domefavor.domefavor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {


    private RecyclerView listview;
    private RecyclerInfoAdapter adapter;
    private List<PriceItem> list;
    private String taskID, personID;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        listview = (RecyclerView) findViewById(R.id.price_list);
        list = new ArrayList<>();

        Intent intent = getIntent();
        Bundle mbundle = intent.getExtras();

        taskID = mbundle.getString("ID");
        personID = mbundle.getString("PersonID");
        listview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        new getPriceTask().execute(taskID);
        super.onResume();
    }

    class getPriceTask extends AsyncTask<String, Void, List<PriceItem>> {
        @Override
        protected List<PriceItem> doInBackground(String... ID) {
            List<PriceItem> items = null;
            System.out.println(ID[0]);
            try {
                items = Server.getAllPrice(ID[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("size"+items.size());
            return items;
        }

        @Override
        protected void onPostExecute(List<PriceItem> items) {
            if (items.size() != 0) {
                for (int i = 0; i < items.size(); i++) {
                    list.add(items.get(i));
                }

                adapter = new RecyclerInfoAdapter(list);
                listview.setAdapter(adapter);
                displayAdpater();
                if (status == 1) {
                    finish();
                }
            }
            else if (items.size() == 0){
                new AlertDialog.Builder(InfoActivity.this)
                        .setTitle("Notification")
                        .setMessage("No one has bid your task!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                status = 1;
                                finish();
                            }
                        })
                        .show();
            }
        }
    }

    public void displayAdpater() {
        listview.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                new AlertDialog.Builder(InfoActivity.this)
                        .setTitle("Accept")
                        .setMessage("Do you want to accept this offer?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new closeTask().execute(taskID, personID);
//                                try {
//                                    Mail.sendEmail(personID, "Notification", "You have choosen a task!");
//                                    Mail.sendEmail(list.get(position - 1).getPersonID(), "Notification", "Your bid is successful!");
//                                } catch (MessagingException e) {
//                                    e.printStackTrace();
//                                }
                                status = 1;
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }));
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
