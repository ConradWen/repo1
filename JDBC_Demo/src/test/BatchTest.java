package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import jdbc.DBUtils;

public class BatchTest {

	/*
	 * 测试Statement插入10条记录
	 * JUnit测试
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
			System.out.println("Statement插入数据耗时："+(end-start));
			DBUtils.commitTransaction(conn);
		} catch (Exception e) {
			e.printStackTrace();
			DBUtils.rollbackTransaction(conn);
		}finally {
			DBUtils.close(conn, null, stat);
			
		}
	}
}
