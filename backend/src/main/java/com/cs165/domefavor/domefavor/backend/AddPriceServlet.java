package com.cs165.domefavor.domefavor.backend;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Alex on 16/4/17.
 */
public class AddPriceServlet extends HttpServlet {
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
        String id = "";
        String price = "";
        String taker = "";
        try {
            price = ob.getString("price");
            id = ob.getString("taskID");
            taker = ob.getString("personID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (id == null || id.equals("")) {
            req.setAttribute("_retStr", "invalid input");
            getServletContext().getRequestDispatcher("/query_result.jsp")
                    .forward(req, resp);
            return;
        }
        PriceDatastore.deletedup(id, taker);
        Price prices = new Price(price, id, taker);
        boolean ret = PriceDatastore.add(prices);
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

