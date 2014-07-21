<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Biting, a Bing-based image reranking engine</title>

<script type="text/javascript">

	function getBingImage()
	{
		num = parseInt(document.getElementById("bingNO").value)
		if(!(num>=10 && num<=50))
		{
			alert("please choose a number from 10 to 50!")
			num = 30
			document.getElementById("bingNO").value = 30
		}
		query = document.getElementById("bingquery").value
		var xmlhttp;
		if (window.XMLHttpRequest)
  		{// code for IE7+, Firefox, Chrome, Opera, Safari
  			xmlhttp=new XMLHttpRequest();
  		}
		else
  		{// code for IE6, IE5
  			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  		}
  		document.getElementById("loading").innerHTML = "<img src=res/loading.gif />";
  		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    	document.getElementById("cnt").innerHTML=xmlhttp.responseText;
		    	document.getElementById("loading").innerHTML = "";
		    }
		}
		if(query.length>0)
		{
  			xmlhttp.open("GET","bing.jsp?bingquery="+query+"&num="+num,true);
			xmlhttp.send();
		}
	}
	function rerankBingImage()
	{
		num = parseInt(document.getElementById("bingNO").value)
		if(!(num>=10 && num<=50))
		{
			alert("please choose a number from 10 to 50!")
			num = 30
			document.getElementById("bingNO").value = 30
		}
		query = document.getElementById("bingquery").value
		var xmlhttp;
		if (window.XMLHttpRequest)
  		{// code for IE7+, Firefox, Chrome, Opera, Safari
  			xmlhttp=new XMLHttpRequest();
  		}
		else
  		{// code for IE6, IE5
  			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  		}
  		document.getElementById("loading2").innerHTML = "<img src=res/loading.gif />";
  		
  		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    	document.getElementById("cnt").innerHTML=xmlhttp.responseText;
		    	document.getElementById("loading2").innerHTML = "";
		    }
		}
		if(query.length>0)
		{
  			xmlhttp.open("GET","rerank.jsp?bingquery="+query+"&num="+num,true);
			xmlhttp.send();
		}
	}
</script>
</head>
<body>
<div id="cont">
	<div id="lg" align="center">
		<p>
			<img src="res/logo_b.png">
		</p>
	</div>
	<div id="fm" align="center">	
	<input type="text" id="bingquery" maxlength="100">
	<span>
		<input type="button" value="Bing Image" id="bingbutton" onclick="getBingImage()">
	</span>
	<span id="loading">
	</span>
	<span>
		<input type="button" value="Rerank Image" id="rerankbutton" onclick="rerankBingImage()">
	</span>
	<span id="loading2">
	</span>
	</div>
	<p/>
	<div align="center">
	We will return
	<input type="text" id="bingNO" value="30" maxlength="3" size="3">(10-50)
	images. More images need more response time.
	</div>
	<p/>
	<p/>
	<p/>
	<p/>
	<div id="cnt">
	</div>
	<p/>
	<div id="anno" align="center">
		Goto <a href="./index.jsp">Anno: an image auto-annotation site.</a>
	</div>
</div>
</body>
</html>