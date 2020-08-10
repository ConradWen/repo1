package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import jdbc.DBUtils;

public class BatchTest {

	/*
	 * ����Statement����10����¼
	 * JUnit����
	 */
	@Test
	public void insertStatement() {
		Connection conn = null;
		Statement stat= null;
		String sql=null;
		try {
			conn=DBUtils.getConnection();
			stat=conn.createStatement();
			DBUtils.startTransaction(conn);
			long start = System.currentTimeMillis();
			for(int i=1;i<100;i++) {
			sql="INSERT INTO students(sname,age)VALUES('name',"+i+")";
			stat.executeUpdate(sql);
			
			}
			long end = System.currentTimeMillis();
			System.out.println("Statement�������ݺ�ʱ��"+(end-start));
			DBUtils.commitTransaction(conn);
		} catch (Exception e) {
			e.printStackTrace();
			DBUtils.rollbackTransaction(conn);
		}finally {
			DBUtils.close(conn, null, stat);
			
		}
	}
}
