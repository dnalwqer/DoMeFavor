package com.cs165.domefavor.domefavor.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTaskServlet extends HttpServlet {
	private final String subject = "Your new task";
	private final String content = "a";
	private final String postersubject = "";
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
		String email = "";

		try {
			id = ob.getString("taskID");
			email = ob.getString("personID");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ContactDatastore.delete(id);
		PriceDatastore.deleteid(id);
//		MessagingEndpoint.sendMessage("D" + id);
//		resp.sendRedirect("/querytask.do");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doGet(req, resp);
	}
}
