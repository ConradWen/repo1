<%@page import="java.util.Date"%>
<%@page import="model.User"%>
<%@page import="java.sql.Statement"%>
<%@page import="jdbc.DBUtils"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert</title>
</head>
<body>
	<%
	    //创建User.java的对象
	    User u = new User();
	    //拿到index.jsp提交的内容
		request.setCharacterEncoding("UTF-8");
	    u.setLogname(request.getParameter("logname"));
	    u.setPassword(request.getParameter("password"));
	    u.setPhone(request.getParameter("phone"));
	    u.setAddress(request.getParameter("address"));
	    u.setRealname(request.getParameter("realname"));
	    u.setDate(new Date());
	    /*statment 所用的sql
		String sql = "INSERT into `user`(logname,`password`,phone,address,realname,date) VALUES('" + u.getLogname() + "','"
				+ u.getPassword() + "','" + u.getPhone() + "','" + u.getAddress() + "','" + u.getRealname() + "','" +new java.sql.Date(u.getDate().getTime())+"')";
	    */
		// 小知识：拿到时间 new java.sql.Date(new java.util.Date().getTime())
		//PreparedStatment 用的sql
		String sql = "INSERT into `user`(logname,`password`,phone,address,realname,date) VALUES(?,?,?,?,?,?)";
		//int count = DBUtils.IUD(sql);//使用statement时
		//以下这个方式是用preparedstatement时
		int count = DBUtils.IUD(sql,u.getLogname(),u.getPassword(),u.getPhone(),u.getAddress(),u.getRealname(),new java.sql.Date(u.getDate().getTime()));
		if (count > 0) {
			out.print("插入成功");
			out.print("<br/>");
			out.print("<span style='color:green;font-size:30px;'>注册成功</span>");
		}
	%>
</body>
</html>