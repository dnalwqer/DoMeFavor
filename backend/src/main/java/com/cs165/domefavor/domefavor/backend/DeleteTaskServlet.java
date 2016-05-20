package com.cs165.domefavor.domefavor.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTaskServlet extends HttpServlet {

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

		String id = "";
		String email = "";

		try {
			ob = list.getJSONObject(0);
			id = ob.getString("taskID");
			email = ob.getString("personID");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ContactDatastore.delete(id);
		PriceDatastore.delete(id);
//		MessagingEndpoint.sendMessage("D" + id);
		resp.sendRedirect("/querytask.do");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doGet(req, resp);
	}
}
