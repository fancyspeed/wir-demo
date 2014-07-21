<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.jspsmart.upload.*" %>
<%@ page import="AnnoData.*" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.imageio.*" %>
<%@ page import="javax.swing.*" %>
<%@ page import="java.awt.image.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Image Auto-Annotation Demo</title>
</head>
<body>
	<div id="cont" align="center">
		<div id="lg">
			<p>
				<img src="./res/logo_a.PNG">
			</p>
		</div>
	</div>
<%
	SmartUpload smart = new SmartUpload();
	smart.initialize(pageContext);
	//String filename = smart.getRequest().getParameter("imagefile");
	//System.out.println(filename);
//	if(smart.getFiles().getSize() > 0) {
	//	smart.setAllowedFilesList("jpg");
		smart.upload();
		System.out.println("files:" + smart.getFiles().getSize());
		//System.out.println(smart.getFiles().getFile(0).getFilePathName());
		//System.out.println(smart.getFiles().getFile(0).getContentString());
		//System.out.println(smart.getFiles().getFile(0).isMissing());
		System.out.println("file 0 size: " + smart.getFiles().getFile(0).getSize());
		//System.out.println(smart.getFiles().getFile(0).getSubTypeMIME());
	 	
		if(	!smart.getFiles().getFile(0).isMissing()
				&&smart.getFiles().getFile(0).getSize() > 0
				&& smart.getFiles().getFile(0).getSize() < 400000) {
			String filepath = smart.getFiles().getFile(0).getFilePathName();
			System.out.println("filepath: " + filepath);
			smart.getFiles().getFile(0).saveAs("./" + filepath);
			smart.getFiles().getFile(0).saveAs("E:\\" + filepath);
		//	byte[] ib = smart.getFiles().getFile(0).getContentString().getBytes();
		//	System.out.println(ib.length);
			
		//	ByteArrayInputStream bais = new ByteArrayInputStream(ib);
		 //   BufferedImage bi = ImageIO.read(bais);
		    
		//    System.out.println(bi.getHeight());
		//    System.out.println(bi.getWidth());
		    		
		    Image img = Toolkit.getDefaultToolkit().getImage("E:\\" + filepath);;
			
			Annotater anno = new Annotater();
			String[] labels = anno.annotation(img, 2);
			%>
			<div align="center">
				<img src=<%=filepath %> height=400 width=300 />
			</div>
			<div align="center">
			Labels:<p/>
			</div>
			<% 
			for(int i=0; i<labels.length; i++) {
				%>
				<div align="center">
				<%=i %>: <%=labels[i] %><p/>
				</div>
				<%
			}
		}
//	}
%>
	<div align="center">
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