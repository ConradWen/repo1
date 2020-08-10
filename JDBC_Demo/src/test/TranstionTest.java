package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.Dao;
import jdbc.DBUtils;

public class TranstionTest {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException {
		Dao dao = new Dao();
		Connection conn = DBUtils.getConnection();
		try {
			//开始事务,默认为true
			conn.setAutoCommit(false);
			
			String sql1="UPDATE `students` SET age=? WHERE id=1";
			dao.iud(conn,sql1, 20);
			
			// 写个异常
			//int i = 10/0;
			
			String sql2="UPDATE `students` SET age=? WHERE id=2";
			dao.iud(conn,sql2, 27);
			
			//提交事务
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//事务回滚，遇到系统异常恢复到原来的状态
		}finally {
			DBUtils.close(conn, null, null);
		}

	}

}
