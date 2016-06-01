package com.cs165.domefavor.domefavor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

/**
 * Temporary test file
 * Created by xuehanyu on 5/19/16.
 */
public class test_han extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_han);
    }

    public void saveTask(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                TaskItem item = new TaskItem("", "buy", "-72.2862464", "43.7089025", new java.util.Date().toString(), "good", 12.3,
                        "cs165sp@gmail.com", "", "sd");
                try {
                    Server.saveNewTask(item);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void allTask(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.getAllTasks("-72.2862464", "43.7089025");
                } catch (Exception e) {}
            }
        }).start();
    }

    public void personTask(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<TaskItem> tasks = Server.getPersonTasks("asd");
                    for (int i = 0; i < tasks.size(); ++i) {
                        System.out.println(tasks.get(i).getTaskID());
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void changePrice(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Server.changePrice(23.4, "1464050662004buy","asd");
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void allPrice(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<PriceItem> prices = Server.getAllPrice("");
                    for (int i = 0; i < prices.size(); ++i) {
                        System.out.println(prices.get(i).getPersonID());
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void closeTask(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.closeOneTask("1464049740015buy", "cs165sp@gmail.com", TaskItem.withoutCredit);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    public void saveProfile(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.saveProfile(new PriceItem(0, "cs165sp@gmail.com", "female", "23", "asd"));
                } catch (Exception e) {
                }
            }
        }).start();
    }
}
//package com.cs165.domefavor.domefavor;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//
//import java.util.List;
//
///**
// * Created by xuehanyu on 5/19/16.
// */
//public class test_han extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_han);
//    }
//
//    public void saveTask(View v){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                TaskItem item = new TaskItem("", "buy", "-70.970479", "41.613032", new java.util.Date().toString(), "good", 12.3,
//                        "cs165sp@gmail.com", "");
//                try {
//                    Server.saveNewTask(item);
//                } catch (Exception e) {
//                }
//            }
//        }).start();
//    }
//
//    public void allTask(View v){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Server.getAllTasks("-70.970479", "41.613032");
//                } catch (Exception e) {}
//            }
//        }).start();
//    }
//
//    public void personTask(View v){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<TaskItem> tasks = Server.getPersonTasks();
//                    for (int i = 0; i < tasks.size(); ++i) {
//                        System.out.println(tasks.get(i).getTaskID());
//                    }
//                } catch (Exception e) {
//                }
//            }
//        }).start();
//    }
//
//    public void changePrice(View v){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Server.changePrice(23.4, "1464050662004buy");
//                } catch (Exception e) {
//                }
//            }
//        }).start();
//    }
//
//    public void allPrice(View v){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<PriceItem> prices = Server.getAllPrice("");
//                    for (int i = 0; i < prices.size(); ++i) {
//                        System.out.println(prices.get(i).getPersonID());
//                    }
//                } catch (Exception e) {
//                }
//            }
//        }).start();
//    }
//
//    public void closeTask(View v){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Server.closeOneTask("1464049740015buy", "cs165sp@gmail.com");
//                } catch (Exception e) {
//                }
//            }
//        }).start();
//    }
//
//    public void saveProfile(View v){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Server.saveProfile(new PriceItem(0, "cs165sp@gmail.com", "female", "23"));
//                } catch (Exception e) {
//                }
//            }
//        }).start();
//    }
//}
