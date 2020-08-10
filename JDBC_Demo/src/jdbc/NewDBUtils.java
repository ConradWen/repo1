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
	�µ�DBUtils����
	����C3P0�������ݿ�����
	*/
	//��������Դ
	private static DataSource dataSource = null;
	static {
		//ȡ��c3p0-config.xml��name=��mysql����һЩ��Ҫ������
		dataSource=new ComboPooledDataSource("mysql");
		
	}
	//��ȡ��Connection
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static User getOneUser(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		User u = null;
		try{
			
			//1,��ȡ�����ݿ������
			conn=NewDBUtils.getConnection();
			//2,д��ѯ���
			//String sql= "SELECT logname,`password`,phone,address,realname,`date` FROM `user` WHERE logname='"+lognameString+"'";
			//3,��ȡstatment����
			stat=conn.prepareStatement(sql);
			
			for(int i=0;i<args.length;i++) {
				stat.setObject(i+1, args[i]);
			}
			//4,ִ��sql���
			rs=stat.executeQuery();
			//5,��rs������У��þ��������е���ֵ,getString()����ֶ������ܼ����غ�``�������������ִ��
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
			//6,�ͷ���Դ������ͨ�ùرշ���
			DBUtils.close(conn, rs, stat);
		}		
		return u;
	}
		
	

}
