package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import model.User;

//import com.mysql.jdbc.Driver;

public class DBUtils {
//	// �ѹ������ɵ���ģʽ
//	private DBUtils() {
//	}
//
//	private static DBUtils dbu = null;
//
//	public static DBUtils getInstance() {
//		if (dbu == null) {
//			dbu = new DBUtils();
//		}
//		return dbu;
//	}
    //���ݿ������
	public static Connection getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Properties pop = new Properties();
		//InputStream in = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");//����ģʽ��Ҫͨ��this.getClass()������
		InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
	    pop.load(in);
		String dirverClass=pop.getProperty("dirverClass");
		String url=pop.getProperty("url");
		String user=pop.getProperty("user");
		String password=pop.getProperty("password");
		
	    
	    //��һ�ַ�����
//		Driver driver = (Driver) Class.forName(dirverClass).newInstance();
//		Properties info = new Properties();
//		info.put("user", user);
//		info.put("password", password);
//		Connection connection = driver.connect(url, info);
//		return connection;
		
		//�ڶ��ַ�����
		Class.forName(dirverClass);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
		
	}
	//IUD�������ģ�ɾ�����ķ���
	public static int IUD(String sql,Object...args) throws SQLException {
		Connection conn = null;
		//Statement stat = null;//statement��������
		PreparedStatement stat=null;//��PreparedStatement��������
		int count = 0;//����Ӱ��ļ�¼����
		try {
			conn = DBUtils.getConnection();
			//stat = conn.createStatement();//statement��Ӧ��createStatement����
			stat=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++) {
				stat.setObject(i+1, args[i]);
			}
			count = stat.executeUpdate();//��count����ִ��sql���Ĵ���
		} catch (Exception e) {
			e.printStackTrace();//��ӡ���쳣·��
		} finally {
			close(conn, null, stat);
		}
		return count;
	}
	//��ѯ��һ��user�õķ���
	public static User getOneUser(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		User u = null;
		try{
			
			//1,��ȡ�����ݿ������
			conn=DBUtils.getConnection();
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
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			//6,�ͷ���Դ������ͨ�ùرշ���
			DBUtils.close(conn, rs, stat);
		}		
		return u;
		
	}
	//�رյ�ͨ�÷���
	public static void close(Connection conn,ResultSet rs,Statement stat) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stat!=null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	//��ȡһ����Ϣ��ͨ�÷���
	public static <T> T getOneData(Class clazz,String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		T entity = null;
		try {
			conn=DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++) {
				stat.setObject(i+1, args[i]);
			}
			rs=stat.executeQuery();
			if(rs.next()) {
				entity=(T) clazz.newInstance();//ͨ�������õ��������͵Ķ���
				//���ȣ���֪��entity����Щ����
				ResultSetMetaData rsmd  = rs.getMetaData();//����rsmd���ڻ�ȡrs�����ж������ҵ�һ���Ƿ���WHERE
				int columncount = rsmd.getColumnCount();//��ȡ���ֶεĸ���
				Map<String, Object> map  = new HashMap<>();
				for(int i=1;i<=columncount;i++) {
					String key = rsmd.getColumnLabel(i);//��ȡ���ڼ���
					Object val = rs.getObject(key);//��ȡ��key��Ӧ��value
					map.put(key, val);//��ӵ�map��ȥ
				}
				for(Entry<String, Object> entry:map.entrySet()) {//����map
					Field field = clazz.getDeclaredField(entry.getKey());//getDeclaredField��ר�����private���εı����ķ���
					field.setAccessible(true);//ʹprivate��������Ч
					field.set(entity,entry.getValue());//
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();		
		}finally {
			DBUtils.close(conn, rs, stat);
		}		
		return entity;	
	}
	
	//����������
	public static void startTransaction(Connection conn) {
		if (conn!=null) {
			try {
				conn.setAutoCommit(false);//����������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//�ر�������
	public static void commitTransaction(Connection conn) {
		if (conn!=null) {
			try {
				conn.commit();//�ύ������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//�ع�������
	public static void rollbackTransaction(Connection conn) {
		if (conn!=null) {
			try {
				conn.rollback();//�ύ������
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
