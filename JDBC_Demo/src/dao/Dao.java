package dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import jdbc.DBUtils;

public class Dao {
	//遍历传入的args 把内容替换掉？
	private PreparedStatement insteadHolder(PreparedStatement stat,Object...args) throws SQLException {
		for(int i=0;i<args.length;i++) {
			stat.setObject(i+1, args[i]);
		}
		return stat;
	}
	//
	private <T> T getEntity(ResultSet rs,Class<?> clazz) throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		@SuppressWarnings("unchecked")
		T entity=(T) clazz.newInstance();//通过反射拿到这种类型的对象
		//首先，不知道entity有哪些属性
		ResultSetMetaData rsmd  = rs.getMetaData();//创建rsmd用于获取rs对象有多少列且第一列是否有WHERE
		int columncount = rsmd.getColumnCount();//获取到字段的个数
		for(int i=1;i<=columncount;i++) {
			String key = rsmd.getColumnLabel(i);//获取到第几列
			Object val = rs.getObject(key);//获取到key对应的value
//			Field field = clazz.getDeclaredField(key);//getDeclaredField是专门针对private修饰的变量的方法
//			field.setAccessible(true);//使private的修饰无效
//			field.set(entity,val);//
			BeanUtils.setProperty(entity, key, val);
		}
		return entity;
	}
	//1,實現增改删
	public int iud(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat=null;//用PreparedStatement创建对象
		int count = 0;//接受影响的记录条数
		try {
			conn = DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			insteadHolder(stat, args);
			count = stat.executeUpdate();//用count接受执行sql语句的次数
		} catch (Exception e) {
			e.printStackTrace();//打印出异常路径
		} finally {
			DBUtils.close(conn, null, stat);
		}
		return count;
	}
	//2,外部传入conn的iud方法,且支持事务处理，conn不能关
	public int iud(Connection conn,String sql,Object...args) {
		PreparedStatement stat=null;//用PreparedStatement创建对象
		int count = 0;//接受影响的记录条数
		try {
			conn = DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			insteadHolder(stat, args);
			count = stat.executeUpdate();//用count接受执行sql语句的次数
		} catch (Exception e) {
			e.printStackTrace();//打印出异常路径
		} finally {
			DBUtils.close(null, null, stat);
		}
		return count;
	}
	
	//获取满足查询一条记录的对象
	public <T> T getOneData(Class<T> clazz,String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		T entity = null;
		try {
			conn=DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			insteadHolder(stat, args);
			rs=stat.executeQuery();
			if(rs.next()) {
				entity=getEntity(rs, clazz);
			}
			
		} catch (Exception e) {
			e.printStackTrace();		
		}finally {
			DBUtils.close(conn, rs, stat);
		}		
		return entity;	
	
	}
	
	//获取满足查询条件的多条记录
	public <T> List<T> getListData(Class<T> clazz,String sql,Object...args){
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		T entity = null;
		List<T> list = new ArrayList<T>();
		try {
			conn=DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			insteadHolder(stat, args);
			rs=stat.executeQuery();
			while(rs.next()) {
				entity=getEntity(rs, clazz);
				list.add(entity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();		
		}finally {
			DBUtils.close(conn, rs, stat);
		}		
		return list;
	}
	
	//获取满足查询条件的某个字段的值或某个字段（count（*））
	public Object getOneColumn(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		try {
			conn=DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			insteadHolder(stat, args);
			rs=stat.executeQuery();
			if(rs.next()) {
				return rs.getObject(1);
			}
		}catch (Exception e) {
			e.printStackTrace();		
		}finally {
			DBUtils.close(conn, rs, stat);
		}		
		return null;
	}
	//获取到执行sql语句后的自增的键值（即id）
	public int insertReturnId(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat=null;//用PreparedStatement创建对象
		ResultSet rs = null;
		int id = 0;
		try {
			conn = DBUtils.getConnection();
			stat=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//Statement.RETURN_GENERATED_KEYS返回的是1
			insteadHolder(stat, args);
			stat.executeUpdate();
			rs=stat.getGeneratedKeys();
			if(rs.next()) {
				id=rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();//打印出异常路径
		} finally {
			DBUtils.close(conn, null, stat);
		}
		return id;
	}
	//专门取图片的方法
	
	public Blob getOneColumnBlob(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs  = null;
		try {
			conn=DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			insteadHolder(stat, args);
			rs=stat.executeQuery();
			if(rs.next()) {
				return rs.getBlob(1);
			}
		}catch (Exception e) {
			e.printStackTrace();		
		}finally {
			DBUtils.close(conn, rs, stat);
		}
		return null;	
	}
	

}
