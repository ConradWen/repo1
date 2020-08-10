<%@page import="jdbc.DBUtils"%>
<%@page import="model.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
学生：
<%
String id =request.getParameter("id");
String sql="SELECT id id,sname sname,age age FROM students WHERE id=1";
Student st = DBUtils.getOneData(Student.class, sql);
out.print(st);

%>
</body>
</html>