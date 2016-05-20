package com.cs165.domefavor.domefavor;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.List;

/**
 * Created by xuehanyu on 5/19/16.
 */
public class Server {
    private static final String SERVER = "";
    private static final String SAVETASK = "";
    private static final String ALLTASK = "";
    private static final String PERSONTASK = "";
    private static final String CHANGEPRICE = "";

    public static void saveNewTask(TaskItem item) throws Exception{
        URL url = getUrl(SERVER+SAVETASK);

        JSONObject itemJson = new JSONObject();
        itemJson.put(TaskItem.taskIDS, item.getTaskID());
        itemJson.put(TaskItem.nameS, item.getTaskName());
        itemJson.put(TaskItem.contentS, item.getContent());
        itemJson.put(TaskItem.latitudeS, item.getLatitude());
        itemJson.put(TaskItem.longitudeS, item.getLongitude());
        itemJson.put(TaskItem.priceS, item.getPrice());
        itemJson.put(TaskItem.timeS, item.getTime());
        itemJson.put(TaskItem.personIDS, item.getPersonID());
        itemJson.put(TaskItem.statusS, item.getStatus());

        sendData(itemJson.toString(), url);
    }

    public static List<TaskItem> getAllTasks(String longitude, String latitude) throws Exception{
        URL url = getUrl(SERVER+ALLTASK);

        JSONObject itemJson = new JSONObject();
        itemJson.put(TaskItem.longitudeS, longitude);
        itemJson.put(TaskItem.latitudeS, latitude);

        return tasksFromServer(itemJson, url);
    }

    public static List<TaskItem> getPersonTasks() throws Exception{
        String personID = "";   //han get personID
        URL url = getUrl(SERVER+PERSONTASK);

        JSONObject itemJson = new JSONObject();
        itemJson.put(TaskItem.personIDS, personID);

        return tasksFromServer(itemJson, url);
    }

    public static void changePrice(double price, String taskID) throws Exception{
        String personID = "";   //han get personID
        URL url = getUrl(SERVER+CHANGEPRICE);

        JSONObject itemJson = new JSONObject();
        itemJson.put(TaskItem.priceS, price);
        itemJson.put(TaskItem.taskIDS, taskID);

        sendData(itemJson.toString(), url);
    }

    private static List<TaskItem> tasksFromServer(JSONObject itemJson, URL url){
        String response = sendData(itemJson.toString(), url);
        List<TaskItem> tasks = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(response);
            for(int i = 0 ; i < jsonArray.length() ; ++i){
                JSONObject taskJson = jsonArray.getJSONObject(i);
                TaskItem task = new TaskItem(taskJson.getString(TaskItem.taskIDS),
                        taskJson.getString(TaskItem.nameS),
                        taskJson.getString(TaskItem.longitudeS),
                        taskJson.getString(TaskItem.latitudeS),
                        taskJson.getString(TaskItem.timeS),
                        taskJson.getString(TaskItem.contentS),
                        taskJson.getDouble(TaskItem.priceS),
                        taskJson.getString(TaskItem.personIDS),
                        taskJson.getString(TaskItem.statusS));
                tasks.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private static URL getUrl(String urlString){
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + urlString);
        }
        return url;
    }

    private static String sendData(String body, URL url){
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
            // Get Response
            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
            rd.close();
            return response.toString();
        }
        catch(Exception e) {
            e.printStackTrace();
            return "";
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
