<%@page import="jdbc.NewDBUtils"%>
<%@page import="model.User"%>
<%@page import="jdbc.DBUtils"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userList</title>
</head>
<body>
用户信息的列表:
<%
	String lognameString=request.getParameter("logname2");

	String sql="SELECT logname,password,phone,address,realname,date FROM user WHERE logname=?";
	User u = NewDBUtils.getOneUser(sql, lognameString);
	if(u!=null){
		out.print(u);
	}else{
		out.print("查无此人");
	}
 
%>
</body>
</html>