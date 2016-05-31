package com.cs165.domefavor.domefavor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {


    private RecyclerView listview;
    private RecyclerInfoAdapter adapter;
    private List<PriceItem> list;
    private List<Uri> url1;
    private String taskID, personID;
    private int status = 0;

    private String taskname, taskcontent, tasktime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        listview = (RecyclerView) findViewById(R.id.price_list);
        list = new ArrayList<>();
        url1 = new ArrayList<>();

        Intent intent = getIntent();
        Bundle mbundle = intent.getExtras();

        taskID = mbundle.getString("ID");
        personID = mbundle.getString("PersonID");
        taskname = mbundle.getString("TaskName");
        taskcontent = mbundle.getString("Content");
        tasktime = mbundle.getString("Time");
        listview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        list.clear();
        url1.clear();
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

                for (int i = 0; i < items.size(); i++) {
                    new downloadTask().execute(list.get(i).getUrl());
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
//                NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(InfoActivity.this);
//
//                dialogBuilder
//                        .withTitle("Accept")                                  //.withTitle(null)  no title
//                        .withTitleColor("#FFFFFF")                                  //def
//                        .withDividerColor("#11000000")                              //def
//                        .withMessage("Do you want to accept this offer?")                     //.withMessage(null)  no Msg
//                        .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
//                        .withDialogColor("#727272")                               //def  | withDialogColor(int resid)
//                        .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
//                        .withDuration(700)                                          //def
//                        .withEffect(Effectstype.RotateBottom)                                         //def Effectstype.Slidetop
//                        .withButton1Text("OK")                                      //def gone
//                        .withButton2Text("Cancel")                                  //def gone
//                        .setButton1Click(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                new closeTask().execute(taskID, personID);
////                                try {
////                                    Mail.sendEmail(personID, "Notification", "You have choosen a task!");
////                                    Mail.sendEmail(list.get(position - 1).getPersonID(), "Notification", "Your bid is successful!");
////                                } catch (MessagingException e) {
////                                    e.printStackTrace();
////                                }
//                                status = 1;
//                            }
//                        })
//                        .setButton2Click(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                            }
//                        })
//                        .show();
                new AlertDialog.Builder(InfoActivity.this)
                        .setTitle("Accept")
                        .setMessage("Do you want to accept this offer?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new closeTask().execute(taskID, personID);
                                status = 1;
                                send(getApplication(), list.get(position-1).getPersonID(),taskname
                                        , "Your bid for " + taskname + "has been choosen!" + "\n" + "Time: " + tasktime + "\n" + "Content:" + taskcontent + "\n" + "The price will be :" + list.get(position-1).getPrice());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        }));
    }

    class closeTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... ID) {
            try {
                Server.choosePrice(ID[0], ID[1]);
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

    class downloadTask extends AsyncTask<String, Void, Uri> {
        @Override
        protected Uri doInBackground(String... params) {
            if (!params[0].equals("N/A")) {
                try {
                    String name = "photo" + System.currentTimeMillis();
                    FileOutputStream fos = openFileOutput(name, Context.MODE_WORLD_READABLE);
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
                        url1.add(Uri.fromFile(getFileStreamPath(name)));
                        return Uri.fromFile(getFileStreamPath(name));
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
            if (url1.size() == list.size()) {
                adapter = new RecyclerInfoAdapter(list, url1);
                listview.setAdapter(adapter);
                displayAdpater();
                if (status == 1) {
                    finish();
                }
            }
        }
    }

    public static void send(Context context, String receiver, String taskname, String content){
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        data.setData(Uri.parse("mailto:" + receiver));
        data.putExtra(Intent.EXTRA_SUBJECT, "New Task From DoMeFavor:" + taskname);
        data.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(data);
    }
}
