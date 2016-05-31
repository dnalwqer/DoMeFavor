package com.cs165.domefavor.domefavor.backend;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Alex on 16/4/17.
 */
public class AddTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String qs = req.getParameter("data");
//        JSONArray list = null;
//        try {
//            list = new JSONArray(qs);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        JSONObject ob = null;
        try {
            ob = new JSONObject(qs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String taskName = "";
        String lng = "";
        String lat = "";
        String time= "";
        String content = "";
        String price = "";
        String poster = "";
        try {
//            ob = list.getJSONObject(0);
            taskName = ob.getString("taskName");
            lng = ob.getString("longitude");
            lat = ob.getString("latitude");
            time= ob.getString("time");
            content = ob.getString("content");
            price = ob.getString("price");
            poster = ob.getString("personID");
        } catch (JSONException e) {
                e.printStackTrace();
        }

        if (taskName == null || taskName.equals("")) {
            req.setAttribute("_retStr", "invalid input");
            getServletContext().getRequestDispatcher("/query_result.jsp")
                    .forward(req, resp);
            return;
        }

        Contact contact = new Contact(taskName, lat, lng, time, content, price, poster);
        contact.startTime = System.currentTimeMillis(); //@han

        boolean ret = ContactDatastore.add(contact);
        if (ret) {
            req.setAttribute("_retStr", "Add contact " + contact.id + " succ");
//            MessagingEndpoint msg = new MessagingEndpoint();
//            msg.sendMessage("Added");

            ArrayList<Contact> result = new ArrayList<Contact>();
            result.add(contact);
            req.setAttribute("result", result);
        } else {
            req.setAttribute("_retStr", contact.id + " exists");
        }

        JSONArray finalResult = new JSONArray();

        JSONObject cur = new JSONObject();
        try {
                cur.put("id",contact.id);
        } catch (JSONException e) {
                e.printStackTrace();
        }
        finalResult.put(cur);

        resp.setContentType("text");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(finalResult.toString());
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }

}

