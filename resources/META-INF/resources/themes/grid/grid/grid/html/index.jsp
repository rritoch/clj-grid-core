<%@ page language="java" import="java.util.*,clojure.lang.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

 PersistentArrayMap view = (PersistentArrayMap)request.getAttribute("viewdata");

%>
<html>
<head>
<title>Grid Platform</title>
</head>
<body>
<h2>Grid Platform</h2>
<p>Language jsp</p>
<p>Version <%=  (String)view.get("version") %></p>
</body>
</html>

