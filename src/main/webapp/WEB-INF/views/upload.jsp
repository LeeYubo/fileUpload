<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>文件上传</title>
  </head>
  <script type="text/javascript" src=""></script>

  <body>
	<h1>springMVC字节流输入上传文件</h1>   
    <form name="userForm1" action="<%=path %>/upload" enctype="multipart/form-data" method="post">  
        <div id="newUpload1">
            <input type="file" name="file">
        </div>  
        <input type="button" id="btn_add1" value="增加一行" >  
        <input type="submit" value="上传" >  
    </form>   
    <br>  
    <br>  
    <hr align="left" width="60%" color="#FF0000" size="3">  
    <br>  
    <br>  
    <h1>file.Transto上传文件</h1>   
    <form name="userForm2" action="<%=path %>/upload2" enctype="multipart/form-data" method="post">  
        <div id="newUpload2">  
            <input type="file" name="file">  
        </div>
        <input type="button" id="btn_add2" value="增加一行" >  
        <input type="submit" value="上传" >  
    </form>
    <hr align="left" width="60%" color="#FF0000" size="3">  
    <br>  
    <br>  
    <h1>springMVC包装类上传文件</h1>   
    <form name="userForm2" action="<%=path %>/upload3" enctype="multipart/form-data" method="post">  
        <div id="newUpload2">  
            <input type="file" name="file">  
        </div>
        <input type="button" id="btn_add2" value="增加一行" >  
        <input type="submit" value="上传" >  
    </form>
  </body>  
</html>
