<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	<script>
	function raju()
	{
		document.getElementById("filess").innerHTML="<img src='"+document.r.files.value+"' width='100' height='160'>"
		return true;
	}
	</script>

</head>
<body>
<form action="anno.jsp" method="get">
Name: <input type="text" name="username" />
ame: <input type="text" name="password" />
<input type ="submit" value="ok" />
</form>
	<form name='r'>
	<input type='file' name='files'  accept="*.jpg" onmouseout="raju();">
	<div id='filess'></div>
	<input type='button' onclick='raju();' value='click'>
	</form>
</body>
</html>