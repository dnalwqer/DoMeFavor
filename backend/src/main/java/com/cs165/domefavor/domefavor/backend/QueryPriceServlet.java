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
 * Created by Alex on 16/5/19.
 */
public class QueryPriceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String qs = req.getParameter("data");
        JSONObject ob = new JSONObject(qs);
        String name = ob.getString("taskID");

        ArrayList<Price> result = PriceDatastore.query(name);
        req.setAttribute("result", result);

        JSONArray finalResult = new JSONArray();
        for (Price price: result) {
            JSONObject cur = new JSONObject();
            try {
                cur.put("price", price.price);
//                Profile profile = ProfileDatastore.query(price.taker).get(0);
//                cur.put("age", profile.age);
//                cur.put("gender", profile.gender);
//                cur.put("personID", profile.email);
                cur.put("id", price.id);
                cur.put("personID", price.taker);
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
