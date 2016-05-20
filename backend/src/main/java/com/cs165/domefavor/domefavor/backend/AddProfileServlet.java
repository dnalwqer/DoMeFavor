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
public class AddProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String qs = req.getQueryString();
        JSONArray list = null;
        try {
            list = new JSONArray(qs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject ob = null;
        String age = "";
        String gender = "";
        String taker = "";
        try {
            ob = list.getJSONObject(0);
            age = ob.getString("age");
            gender = ob.getString("gender");
            taker = ob.getString("personID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (taker == null || taker.equals("")) {
            req.setAttribute("_retStr", "invalid input");
            getServletContext().getRequestDispatcher("/query_result.jsp")
                    .forward(req, resp);
            return;
        }

        Profile profiles = new Profile(age, taker, gender);
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

        getServletContext().getRequestDispatcher("/query_result.jsp").forward(
                req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }

}

