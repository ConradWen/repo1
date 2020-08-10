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
			//��ʼ����,Ĭ��Ϊtrue
			conn.setAutoCommit(false);
			
			String sql1="UPDATE `students` SET age=? WHERE id=1";
			dao.iud(conn,sql1, 20);
			
			// д���쳣
			//int i = 10/0;
			
			String sql2="UPDATE `students` SET age=? WHERE id=2";
			dao.iud(conn,sql2, 27);
			
			//�ύ����
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//����ع�������ϵͳ�쳣�ָ���ԭ����״̬
		}finally {
			DBUtils.close(conn, null, null);
		}

	}

}
