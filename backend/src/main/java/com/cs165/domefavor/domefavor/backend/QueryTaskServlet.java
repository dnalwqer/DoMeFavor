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

		String qs = req.getQueryString();
		if(qs == null||qs.equals("") ) {
			return;
		}
		JSONArray list = null;
		try {
			list = new JSONArray(qs);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject ob = null;

		String lat = "";
		String lng = "";
		String email = "";

		try {
			ob = list.getJSONObject(0);
			if(ob.has("longitude")){
				lng = ob.getString("longitude");
				lat = ob.getString("latitude");
				result = ContactDatastore.queryloca(lat, lng);
			} else if (ob.has("personID")){
				email = ob.getString("personID");
				result = ContactDatastore.queryname(email);
			} else result = ContactDatastore.query("");

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
			} catch (JSONException e) {
				e.printStackTrace();
			}
			finalResult.put(cur);
		}
		resp.setContentType("text");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(finalResult.toString());

		req.setAttribute("result", result);
		getServletContext().getRequestDispatcher("/query_result.jsp").forward(
				req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doGet(req, resp);
	}
}
