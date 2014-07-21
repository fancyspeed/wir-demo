<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="BingImage.*" %>
<%@ page import="java.lang.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div align="center">
<%
	String query = request.getParameter("bingquery");
	String snum = request.getParameter("num");
	System.out.println(snum);
	int num = Integer.parseInt(snum);
	if(query.length() > 0) {
		BingImageSearch bis = new BingImageSearch();
		BingImageResult[] result = bis.getImageResult(query, num, 0);
		for(int i=0; i<result.length; i++) {
		%>
			<a href=<%=result[i].getDisplayUrl() %>><img src=<%=result[i].getThumbnail()%> title=<%=result[i].getTitle() %>/></a>
			<%if(i%5 == 4) {%>
				<p>
		<%
		} 
	}
}
%>
</div>
</body>
</html>