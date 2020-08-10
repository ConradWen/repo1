<%@page import="java.sql.Statement"%>
<%@page import="jdbc.DBUtils"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jdbc试验首页</title>
</head>
<body>
<form action="insertUser2.jsp" method="GET" style="margin:0 auto;padding:20px;width:500px;border:1px #ccc solid;text-align:center;">
用户名：<input type="text" name="logname"/><br/><br/>
密   码：<input type="text" name="password"/><br/><br/>
电话号：<input type="text" name="phone"/><br/><br/>
住   址：<input type="text" name="address"/><br/><br/>
真实姓名：<input type="text" name="realname"/><br/><br/>
<input type="submit" value="提交">
</form>
<br/><br/><br/><br/><br/><br/>
<form action="userList.jsp" method="get" style="margin:0 auto;padding:20px;width:500px;border:1px #ccc solid;text-align:center;">
用户名：<input type="text" name="logname2"/><br/><br/>
<input type="submit" value="提交">
</form>
<br/><br/><br/><br/><br/><br/>
<form action="student.jsp" method="get" style="margin:0 auto;padding:20px;width:500px;border:1px #ccc solid;text-align:center;">
学  生：<input type="text" name="id"/><br/><br/>
<input type="submit" value="提交">
</form>
<br/><br/><br/><br/><br/><br/>
<form action="login.jsp" method="get" style="margin:0 auto;padding:20px;width:500px;border:1px #ccc solid;text-align:center;">
用户名：<input type="text" name="logname3"/><br/><br/>
密   码：<input type="text" name="password3"/><br/><br/>
<input type="submit" value="提交">
</form>
</body>
</html>