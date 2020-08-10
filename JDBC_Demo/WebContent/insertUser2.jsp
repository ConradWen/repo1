<%@page import="java.sql.PreparedStatement"%>
<%@page import="jdbc.DBUtils"%>
<%@page import="java.sql.Connection"%>
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
	    //1，接受表单数据
	    request.setCharacterEncoding("utf-8");
		String logname = request.getParameter("logname");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String realname = request.getParameter("realname");		
		
		//2，创建数据库连接
		Connection conn = DBUtils.getConnection();
		
		//3，写sql语句
		String sql = "INSERT into `user`(logname,`password`,phone,address,realname,date) VALUES(?,?,?,?,?,?)" ;
		
		//4,创建PreparedStatement对象
		PreparedStatement stat = conn.prepareStatement(sql);
		
		//5，把占位符？替换成需要的数据
		stat.setObject(1, logname);
		stat.setObject(2, password);
		stat.setObject(3, phone);
		stat.setObject(4, address);
		stat.setObject(5, realname);
		stat.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
		
		//6，执行,因为第4步已经传入了sql所以不需要再把sql传入
		stat.executeUpdate();
		
		//7,调用DBUtils里的close方法关闭数据库的所有连接
		DBUtils.close(conn, null, stat);
		
	%>

</body>
</html>