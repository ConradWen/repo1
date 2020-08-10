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
	//���������args �������滻����
	private PreparedStatement insteadHolder(PreparedStatement stat,Object...args) throws SQLException {
		for(int i=0;i<args.length;i++) {
			stat.setObject(i+1, args[i]);
		}
		return stat;
	}
	//
	private <T> T getEntity(ResultSet rs,Class<?> clazz) throws InstantiationException, IllegalAccessException, SQLException, InvocationTargetException {
		@SuppressWarnings("unchecked")
		T entity=(T) clazz.newInstance();//ͨ�������õ��������͵Ķ���
		//���ȣ���֪��entity����Щ����
		ResultSetMetaData rsmd  = rs.getMetaData();//����rsmd���ڻ�ȡrs�����ж������ҵ�һ���Ƿ���WHERE
		int columncount = rsmd.getColumnCount();//��ȡ���ֶεĸ���
		for(int i=1;i<=columncount;i++) {
			String key = rsmd.getColumnLabel(i);//��ȡ���ڼ���
			Object val = rs.getObject(key);//��ȡ��key��Ӧ��value
//			Field field = clazz.getDeclaredField(key);//getDeclaredField��ר�����private���εı����ķ���
//			field.setAccessible(true);//ʹprivate��������Ч
//			field.set(entity,val);//
			BeanUtils.setProperty(entity, key, val);
		}
		return entity;
	}
	//1,���F����ɾ
	public int iud(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat=null;//��PreparedStatement��������
		int count = 0;//����Ӱ��ļ�¼����
		try {
			conn = DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			insteadHolder(stat, args);
			count = stat.executeUpdate();//��count����ִ��sql���Ĵ���
		} catch (Exception e) {
			e.printStackTrace();//��ӡ���쳣·��
		} finally {
			DBUtils.close(conn, null, stat);
		}
		return count;
	}
	//2,�ⲿ����conn��iud����,��֧��������conn���ܹ�
	public int iud(Connection conn,String sql,Object...args) {
		PreparedStatement stat=null;//��PreparedStatement��������
		int count = 0;//����Ӱ��ļ�¼����
		try {
			conn = DBUtils.getConnection();
			stat=conn.prepareStatement(sql);
			insteadHolder(stat, args);
			count = stat.executeUpdate();//��count����ִ��sql���Ĵ���
		} catch (Exception e) {
			e.printStackTrace();//��ӡ���쳣·��
		} finally {
			DBUtils.close(null, null, stat);
		}
		return count;
	}
	
	//��ȡ�����ѯһ����¼�Ķ���
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
	
	//��ȡ�����ѯ�����Ķ�����¼
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
	
	//��ȡ�����ѯ������ĳ���ֶε�ֵ��ĳ���ֶΣ�count��*����
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
	//��ȡ��ִ��sql����������ļ�ֵ����id��
	public int insertReturnId(String sql,Object...args) {
		Connection conn = null;
		PreparedStatement stat=null;//��PreparedStatement��������
		ResultSet rs = null;
		int id = 0;
		try {
			conn = DBUtils.getConnection();
			stat=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);//Statement.RETURN_GENERATED_KEYS���ص���1
			insteadHolder(stat, args);
			stat.executeUpdate();
			rs=stat.getGeneratedKeys();
			if(rs.next()) {
				id=rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();//��ӡ���쳣·��
		} finally {
			DBUtils.close(conn, null, stat);
		}
		return id;
	}
	//ר��ȡͼƬ�ķ���
	
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
