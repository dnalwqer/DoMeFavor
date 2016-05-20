package com.cs165.domefavor.domefavor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

/**
 * Created by xuehanyu on 5/19/16.
 */
public class test_han extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_han);
    }

    public void saveTask(View v){
        TaskItem item = new TaskItem("","buy","-70.970479","41.613032",new java.util.Date().toString(), "good", 12.3, "dart", "");
        try {
            Server.saveNewTask(item);
        }catch(Exception e){}
    }

    public void allTask(View v){
        try {
            Server.getAllTasks("-70.970479","41.613032");
        }catch(Exception e){}
    }

    public void personTask(View v){
        try {
            List<TaskItem> tasks = Server.getPersonTasks();
            for(int i = 0 ; i < tasks.size() ; ++i){
                System.out.println(tasks.get(i).getTaskID());
            }
        }catch(Exception e){}
    }

    public void changePrice(View v){
        try {
            Server.changePrice(23.4, "");
        }catch(Exception e){}
    }

    public void allPrice(View v){
        try {
            List<PriceItem> prices = Server.getAllPrice("");
            for(int i = 0 ; i < prices.size() ; ++i){
                System.out.println(prices.get(i).getPersonID());
            }
        }catch(Exception e){}
    }

    public void closeTask(View v){
        try {
            Server.closeOneTask("","dart");
        }catch(Exception e){}
    }
}
