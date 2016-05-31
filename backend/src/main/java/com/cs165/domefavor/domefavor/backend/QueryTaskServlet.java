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
					JSONArray finalResult = new JSONArray();
					if(result != null && result.size() != 0)
					for(Contact task: result){
						finalResult.put(taskConstruct(task));
					}

					ArrayList<Price> list = PriceDatastore.queryEmail(email);
					if (list != null && list.size() != 0)
					for(Price cur : list){
						ArrayList<Contact> res = ContactDatastore.queryid(cur.id);
						if(res != null && res.size() != 0)
						finalResult.put(bidConstruct(res.get(0)));
					}

					resp.setContentType("text");
					resp.setCharacterEncoding("UTF-8");
					resp.getWriter().write(finalResult.toString());
					return;
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
				profileadd(cur);
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

	public JSONObject taskConstruct(Contact task){
		JSONObject cur = new JSONObject();
		try {
			cur.put("taskName", task.taskName);
			cur.put("content",task.content);
			cur.put("time",task.time);
			cur.put("taskID",task.id);
			cur.put("latitude", task.lat);
			cur.put("longitude",task.lng);
			cur.put("personID",task.poster);
			cur.put("price", task.price);
			if(task.status.equals(Contact.status_tobebid)) {     //@han
				cur.put("status", "post");
				bidersadd(cur);
			}
			else {
				cur.put("status", Contact.status_tobefinish);
				cur.put("biders", task.taker);
			}
			profileadd(cur);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cur;
	}

	public JSONObject bidConstruct(Contact task){
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
			cur.put("status","take");
			profileadd(cur);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return cur;
	}

	public JSONObject profileadd(JSONObject cur){
		ArrayList<Profile> list = ProfileDatastore.query(cur.getString("personID"));
		Profile profile = (list == null || list.size() == 0) ? new Profile("N/A", "N/A", "N/A", "N/A") : list.get(0);
		cur.put("age", profile.age);
		cur.put("gender", profile.gender);
		cur.put("url", profile.url);
		return cur;
	}

	public void bidersadd(JSONObject cur){
		cur.put("biders", PriceDatastore.query(cur.getString("taskID")).size() + "");
	}
}
