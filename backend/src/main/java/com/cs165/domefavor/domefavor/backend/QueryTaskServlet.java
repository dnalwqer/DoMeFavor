package com.cs165.domefavor.domefavor.backend;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryTaskServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
//		String name = req.getParameter("id");
		ArrayList<Contact> result = null;

		String qs = req.getParameter("data");

		String lat = "";
		String lng = "";
		String email = "";

		try {

			if(qs == null || qs.equals(""))
				result = ContactDatastore.query("");
			else {
				JSONObject ob = new JSONObject(qs);
				if (ob.has("longitude")) {
					lng = ob.getString("longitude");
					lat = ob.getString("latitude");
					result = ContactDatastore.queryloca(lat, lng);
				} else if (ob.has("personID")) {
					email = ob.getString("personID");
					result = ContactDatastore.queryname(email);
					ArrayList<Price> list = PriceDatastore.queryEmail(email);
					for(Price cur : list){
						result.addAll(ContactDatastore.queryid(cur.id));
					}
				} else result = ContactDatastore.query("");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray finalResult = new JSONArray();
		for (Contact task: result) {
			JSONObject cur = new JSONObject();
			try {
				cur.put("taskName", task.taskName);
				cur.put("content",task.content);
				cur.put("time",task.time);
				cur.put("taskID",task.id);
				cur.put("latitude", task.lat);
				cur.put("longitude",task.lng);
				cur.put("personID",task.poster);
				cur.put("price",task.price);
				cur.put("status","y");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			finalResult.put(cur);
		}

		resp.setContentType("text");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(finalResult.toString());

		if(qs == null || (lat.equals("") && email.equals(""))){
			req.setAttribute("result", result);
			getServletContext().getRequestDispatcher("/query_result.jsp").forward(
					req, resp);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doGet(req, resp);
	}
}
