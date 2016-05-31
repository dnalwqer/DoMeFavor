package com.cs165.domefavor.domefavor.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xuehanyu on 5/31/16.
 */
public class QueryProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String qs = req.getParameter("data");
        JSONObject ob = null;
        String personID = "";
        try {
            ob = new JSONObject(qs);
            personID = ob.getString("personID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Profile> result = ProfileDatastore.query(personID);
        req.setAttribute("result", result);

        JSONArray finalResult = new JSONArray();
        for (Profile profile: result) {
            JSONObject cur = new JSONObject();
            try {
                cur.put("personID", profile.email);
                cur.put("age", profile.age);
                cur.put("gender", profile.gender);
                cur.put("credit", profile.credit);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finalResult.put(cur);
        }

        resp.setContentType("text");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(finalResult.toString());
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }
}
