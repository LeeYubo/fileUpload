<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>我只是一个简单的首页</title>
  </head>
  
  <body>
    前往文件上传页面，<a href="<%=path %>/fileupload">点我</a>
  </body>
</html>
