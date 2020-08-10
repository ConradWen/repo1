package jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import model.User;

public class NewDBUtils {
	/*
	新的DBUtils试验
	利用C3P0创建数据库连接
	*/
	//创建数据源
	private static DataSource dataSource = null;
	static {
		//取到c3p0-config.xml的name=“mysql”的一些必要的数据
		dataSource=new ComboPooledDataSource("mysql");
		
	}
	//获取到Connection
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static User getOneUser(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		User u = null;
		try{
			
			//1,获取到数据库的连接
			conn=NewDBUtils.getConnection();
			//2,写查询语句
			//String sql= "SELECT logname,`password`,phone,address,realname,`date` FROM `user` WHERE logname='"+lognameString+"'";
			//3,获取statment对象
			stat=conn.prepareStatement(sql);
			
			for(int i=0;i<args.length;i++) {
				stat.setObject(i+1, args[i]);
			}
			//4,执行sql语句
			rs=stat.executeQuery();
			//5,在rs结果集中，拿具体结果集中的数值,getString()里的字段名不能加着重号``否则程序不能正常执行
			if(rs.next()){
				u=new User();
				u.setLogname(rs.getString("logname"));
				u.setPassword(rs.getString("password"));
				u.setPhone(rs.getString("phone"));
				u.setAddress(rs.getString("address"));
				u.setRealname(rs.getString("realname"));
				u.setDate(rs.getDate("date"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//6,释放资源，调用通用关闭方法
			DBUtils.close(conn, rs, stat);
		}		
		return u;
	}
		
	

}
