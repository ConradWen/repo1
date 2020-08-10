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
//	// 把工具类变成单例模式
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
    //数据库的连接
	public static Connection getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Properties pop = new Properties();
		//InputStream in = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");//单例模式需要通过this.getClass()来调用
		InputStream in = DBUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
	    pop.load(in);
		String dirverClass=pop.getProperty("dirverClass");
		String url=pop.getProperty("url");
		String user=pop.getProperty("user");
		String password=pop.getProperty("password");
		
	    
	    //第一种方法：
//		Driver driver = (Driver) Class.forName(dirverClass).newInstance();
//		Properties info = new Properties();
//		info.put("user", user);
//		info.put("password", password);
//		Connection connection = driver.connect(url, info);
//		return connection;
		
		//第二种方法：
		Class.forName(dirverClass);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
		
	}
	//IUD是增，改，删操作的方法
	public static int IUD(String sql,Object...args) throws SQLException {
		Connection conn = null;
		//Statement stat = null;//statement创建对象
		PreparedStatement stat=null;//用PreparedStatement创建对象
		int count = 0;//接受影响的记录条数
		try {
			conn = DBUtils.getConnection();
			//stat = conn.createStatement();//statement对应的createStatement方法
			stat=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++) {
				stat.setObject(i+1, args[i]);
			}
			count = stat.executeUpdate();//用count接受执行sql语句的次数
		} catch (Exception e) {
			e.printStackTrace();//打印出异常路径
		} finally {
			close(conn, null, stat);
		}
		return count;
	}
	//查询到一个user得的方法
	public static User getOneUser(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		User u = null;
		try{
			
			//1,获取到数据库的连接
			conn=DBUtils.getConnection();
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
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			//6,释放资源，调用通用关闭方法
			DBUtils.close(conn, rs, stat);
		}		
		return u;
		
	}
	//关闭的通用方法
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
	//获取一条信息的通用方法
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
				entity=(T) clazz.newInstance();//通过反射拿到这种类型的对象
				//首先，不知道entity有哪些属性
				ResultSetMetaData rsmd  = rs.getMetaData();//创建rsmd用于获取rs对象有多少列且第一列是否有WHERE
				int columncount = rsmd.getColumnCount();//获取到字段的个数
				Map<String, Object> map  = new HashMap<>();
				for(int i=1;i<=columncount;i++) {
					String key = rsmd.getColumnLabel(i);//获取到第几列
					Object val = rs.getObject(key);//获取到key对应的value
					map.put(key, val);//添加到map中去
				}
				for(Entry<String, Object> entry:map.entrySet()) {//遍历map
					Field field = clazz.getDeclaredField(entry.getKey());//getDeclaredField是专门针对private修饰的变量的方法
					field.setAccessible(true);//使private的修饰无效
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
	
	//开启事务处理
	public static void startTransaction(Connection conn) {
		if (conn!=null) {
			try {
				conn.setAutoCommit(false);//开启事务处理
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//关闭事务处理
	public static void commitTransaction(Connection conn) {
		if (conn!=null) {
			try {
				conn.commit();//提交事务处理
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//回滚事务处理
	public static void rollbackTransaction(Connection conn) {
		if (conn!=null) {
			try {
				conn.rollback();//提交事务处理
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
