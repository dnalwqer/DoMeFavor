package com.cs165.domefavor.domefavor.backend;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Alex on 16/4/17.
 */
public class AddProfileServlet extends HttpServlet {
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
        String age = "";
        String gender = "";
        String taker = "";
        String url = "";
        String credit = "0";
        try {
//            ob = list.getJSONObject(0);
            age = ob.getString("age");
            gender = ob.getString("gender");
            taker = ob.getString("personID");
            url = ob.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (taker == null || taker.equals("")) {
            req.setAttribute("_retStr", "invalid input");
            getServletContext().getRequestDispatcher("/query_result.jsp")
                    .forward(req, resp);
            return;
        }
        ArrayList<Profile> oldProfile= ProfileDatastore.query(taker);
        if(oldProfile.size() > 0) {
            ProfileDatastore.delete(taker);
            credit = oldProfile.get(0).credit;
        }
        Profile profiles = new Profile(age, taker, gender, url, credit);
        boolean ret = ProfileDatastore.add(profiles);
//        if (ret) {
//            req.setAttribute("_retStr", "Add contact " + id + " succ");
//            MessagingEndpoint msg = new MessagingEndpoint();
//            msg.sendMessage("Added");
//
//            ArrayList<Contact> result = new ArrayList<Contact>();
//            result.add(contact);
//            req.setAttribute("result", result);
//        } else {
//            req.setAttribute("_retStr", id + " exists");
//        }

//        getServletContext().getRequestDispatcher("/query_result.jsp").forward(
//                req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }

}

