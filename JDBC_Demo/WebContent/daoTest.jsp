<%@page import="model.User"%>
<%@page import="dao.Dao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
Dao dao = new Dao();
User user = null;
//1,测试IDU---成功
//String sql="INSERT INTO students SET id=?,sname=?,age=?";
//dao.iud(sql,7,"zhangsan",25);

//2,测试getOneData
//String sql="SELECT logname,`password`,phone,address,realname,`date` FROM `user` WHERE logname=?";
//user=dao.getOneData(User.class, sql, "jack");
//out.print(user);

//3,测试能不能拿到自增的键id值
String sql="INSERT INTO students(sname,age) VALUES('nihao',25)";
int id = dao.insertReturnId(sql);
out.print(id);
%>

</body>
</html>