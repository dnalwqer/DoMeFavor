package com.cs165.domefavor.domefavor.backend;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xuehanyu on 5/30/16.
 */
public class ChoosePriceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String qs = req.getParameter("data");
        JSONObject ob = null;
        try {
            ob = new JSONObject(qs);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String taskID = "";
        String personID = "";
        try {
            taskID = ob.getString("taskID");
            personID = ob.getString("personID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ContactDatastore.changeStatusToBeFinish(taskID, personID);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }
}
