package com.cs165.domefavor.domefavor;

import android.util.Log;

import org.json.JSONObject;

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

    public static void saveNewTask(TaskItem item) throws Exception{
        URL url;
        String urlString = SERVER+SAVETASK;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + urlString);
        }
        JSONObject itemJson = new JSONObject();
        itemJson.put("taskID", item.getTaskID());
        itemJson.put("taskName", item.getTaskName());
        itemJson.put("content", item.getContent());
        itemJson.put("latitude", item.getLatitude());
        itemJson.put("longitude", item.getLongitude());
        itemJson.put("price", item.getPrice());
        itemJson.put("time", item.getTime());

        String body = itemJson.toString();
        sendData(body, url);
    }

    private static void sendData(String body, URL url){
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
