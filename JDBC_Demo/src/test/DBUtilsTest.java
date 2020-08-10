package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.NEW;

import jdbc.DBUtils;
import jdbc.NewDBUtils;
import model.User;

public class DBUtilsTest {
	@Test
	public void dbutilTest() throws SQLException {
		QueryRunner qr = new QueryRunner();
		Connection conn = null;
		try {
			conn = NewDBUtils.getConnection();
			//String sql ="INSERT INTO students(sname,age)VALUES(?,?);";
			String sql = "SELECT logname,`password`,phone,address,realname,`date` FROM `user` WHERE logname=?";
			//qr.update(conn, sql, "Curry","29");
			User user = qr.query(conn, sql, new rsHandler<User>(), "jack");
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbUtils.close(conn);
		}
	}
	class rsHandler<T> implements ResultSetHandler<T>{

		@Override
		public T handle(ResultSet rs) throws SQLException {
			User user = new User();
			
			if (rs.next()) {
				user.setLogname(rs.getString("logname"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setRealname(rs.getString("realname"));
				user.setDate(rs.getDate("date"));
				
			}
			return (T) user;
		}
		
	}
	
	@Test
	public void dbuTest3() throws SQLException {
		QueryRunner qr = new QueryRunner();
		Connection conn = null;
		try {
			conn=NewDBUtils.getConnection();
			String sql1="SELECT logname,`password`,phone,address,realname,`date` FROM `user`";
			List<User> list = qr.query(conn, sql1, new BeanListHandler<>(User.class));
			System.out.println(list);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			DbUtils.close(conn);
		}
	}

}
