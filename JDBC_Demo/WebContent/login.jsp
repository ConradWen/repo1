<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Statement"%>
<%@page import="jdbc.DBUtils"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
</head>
<body>
	<%
		//1，获取到表单的数据
		request.setCharacterEncoding("UTF-8");
		String logname = request.getParameter("logname3");
		String password = request.getParameter("password3");

		//2,定义需要的对象
		Connection conn = null; //创建数据库连接对象
		//Statement stat = null;   //创建Statement对象
		PreparedStatement ps = null; //创建PreparedStatement对象
		ResultSet rs = null; //创建结果集对象

		try {
			//3，创建数据库连接
			conn = DBUtils.getConnection();

			//4, 写sql语句
			//String sql = "SELECT logname,password From user WHERE logname='"+logname+"' AND password='"+password+"';";
			String sql = "SELECT logname,password FROM user WHERE logname=? AND password=?";

			//5-1，创建Statement对象
			//stat = conn.createStatement();

			//5-2，创建PreparedStatement对象，与Statement进行对比
			ps = conn.prepareStatement(sql);
			ps.setObject(1, logname);
			ps.setObject(2, password);

			//6，执行sql语句
			//rs=stat.executeQuery(sql);
			rs = ps.executeQuery();

			//7，获取到执行sql的结果集
			if (rs.next()) {
				String userString = rs.getString("logname");
				String passString = rs.getString("password");
				out.print("登录成功!" + "<br/>" + "用户名是：" + userString + "<br/>" + "密码是：" + passString);
				/*如果用Statement对象，且sql直接字符串连接的话就可以采用以下攻击方式绕过sql条件语句进行登录
				*攻击方式：
				 用户名输入 ：a' OR password=
				 密码输入： OR '1=1
				*/
			} else {
				out.print("登录失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn, rs, ps);
		}
	%>
</body>
</html>