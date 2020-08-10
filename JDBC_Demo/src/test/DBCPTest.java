package test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

public class DBCPTest {
	
	/*
	 * DBCP���ݿ����ӳ��������ݿ�Ĳ���
	 */
	@Test
	public void dbcpTest() throws SQLException {
		//����BasicDataSource��ʵ����
		BasicDataSource dataSource = new BasicDataSource();

		//�ṩ��Ҫ�����ݿ���Ϣ
		dataSource.setUsername("root");
		dataSource.setPassword("1234");
		dataSource.setUrl("jdbc:mysql://localhost:3306/jdbcDemo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		//���ÿ�ѡ�����ݿ����ӳص�һЩ���ԣ���Щ�����Ƕ����ݿ����ӽ��й���ģ����Ƶľ�����ֵ
		dataSource.setInitialSize(10); //���� ���ݿ����ӳ��г�ʼ����������
		dataSource.setMaxTotal(50);
		dataSource.setMinIdle(5);      // ���� ���ӳ��кͱ������ٿ���������
		dataSource.setMaxWaitMillis(1000*5); //�������ӳط������ӵ��ʱ�䣬��������ʱ�����׳��쳣
		
		//�������ݿ�
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
		
	}
	
	public void dbcpFactoryTest() throws Exception {
		Properties pop  = new Properties();
		InputStream in = DBCPTest.class.getClassLoader().getResourceAsStream("jdbc1.properties");
		pop.load(in);
		
		//��ȡBasicDataSource����
		DataSource dataSource= BasicDataSourceFactory.createDataSource(pop);
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
		
	}

}
