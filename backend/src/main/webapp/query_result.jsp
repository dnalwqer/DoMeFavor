<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cs165.domefavor.domefavor.backend.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query Result</title>
</head>
<body>
	<%
		String retStr = (String) request.getAttribute("_retStr");
		if (retStr != null) {
	%>
	<%=retStr%><br>
	<%
		}
	%>
	<b>
		---------------------------------------------------------------------<br>
		<%
			ArrayList<Contact> resultList = (ArrayList<Contact>) request
					.getAttribute("result");
			if (resultList != null) {
				for (Contact contact : resultList) {
		%> Id:<%=contact.id%>&nbsp; Date Time:<%=contact.time%>&nbsp; 
		Lat:<%=contact.lat%>&nbsp; Lng:<%=contact.lng%>&nbsp; 
		Price:<%=contact.price%>&nbsp; Content:<%=contact.content%>&nbsp; 
		taskname:<%=contact.taskName%>&nbsp; email:<%=contact.poster%>&nbsp; 
		&nbsp;&nbsp;&nbsp; <a
		href="/deletetask.do?id=<%=contact.id%>">delete</a> <br> <%
 	}
 	}
 %>
		---------------------------------------------------------------------<br>
</body>
</html>