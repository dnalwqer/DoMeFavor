package com.cs165.domefavor.domefavor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by xuehanyu on 5/19/16.
 */
public class Server {
    private static final String SERVER = "";
    private static final String SAVETASK = "";

    public static void saveNewTask(TaskItem item){
        URL url;
        String urlString = SERVER+SAVETASK;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + urlString);
        }
        StringBuilder bodyBuilder = new StringBuilder();

        // constructs the POST body using the parameters
        bodyBuilder.append("data=");
        for(int i = 0 ; i < data.size() ; ++i){
            if(i != 0)
                bodyBuilder.append("#");
            databaseItem item = data.get(i);
            bodyBuilder.append(item.getID()).append(",")
                    .append(item.getInputType()).append(",")
                    .append(item.getActivityType()).append(",")
                    .append(item.getDate()).append(" ").append(item.getTime()).append(",")
                    .append(item.getDuration()).append(",")
                    .append(item.getDistance()).append(",")
                    .append(item.getAvgSpeed()).append(",")
                    .append(item.getCalories()).append(",")
                    .append(item.getClimb()).append(",")
                    .append(item.getHeartRate()).append(",")
                    .append(item.getComment());
        }
        String body = bodyBuilder.toString();
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            System.out.println(conn.getURL());
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
