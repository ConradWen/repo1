<%@page import="java.io.FileOutputStream"%>
<%@page import="java.sql.Blob"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
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

//一，插入图片的 方法
//1，绝对路径的方法
//InputStream in = new FileInputStream("D:/fangdong.jpg");

//2，相对路径的方法
/*
InputStream in = new FileInputStream(request.getSession().getServletContext().getRealPath("/")+"Resources/h.jpg");
String sql="INSERT INTO students(sname,age,image) VALUES(?,?,?)";
dao.iud(sql, "zuoye",29,in);
*/

//二，取出图片的方法

String sql = "SELECT image FROM students WHERE id=?";
Blob blob = dao.getOneColumnBlob(sql, 8);
InputStream in =  blob.getBinaryStream();
OutputStream os = new FileOutputStream(request.getSession().getServletContext().getRealPath("/")+"Resources/3.jpg");
//缓冲器
byte[] buffer= new byte[1024];
int len = 0;
while((len=in.read(buffer))!=-1){
	out.println(len);
	os.write(buffer,0,len);
}
in.close();
os.close();

%>
</body>
</html>