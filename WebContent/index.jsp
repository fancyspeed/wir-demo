<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>MSearch: a demo for image auto-annotation and web image reranking</title>
</head>
<body>
<div id="cont" align="center">
	<div id="lg">
		<p>
			<img src="./res/logo_a.PNG">
		</p>
	</div>
	<div id="fm">
		<form name="annoimage" action="anno.jsp" method="post" ENCTYPE="multipart/form-data">
			<input type="file" name="imagefile" maxlength="100">
			<input type="submit" value="Annotate" id="annobutton">
		</form>
	</div>
	<p/>
	<p/>
	<div id="biting">
		Goto <a href="./biting.jsp">Biting: a Bing based Image Reranking Engine</a>
	</div>
</div>
</body>
</html>