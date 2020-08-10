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
	 * DBCP数据库连接池连接数据库的测试
	 */
	@Test
	public void dbcpTest() throws SQLException {
		//创建BasicDataSource的实现类
		BasicDataSource dataSource = new BasicDataSource();

		//提供必要的数据库信息
		dataSource.setUsername("root");
		dataSource.setPassword("1234");
		dataSource.setUrl("jdbc:mysql://localhost:3306/jdbcDemo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		//设置可选的数据库连接池的一些属性，这些属性是对数据库连接进行管理的，控制的具体数值
		dataSource.setInitialSize(10); //设置 数据库连接池中初始化的连接数
		dataSource.setMaxTotal(50);
		dataSource.setMinIdle(5);      // 设置 连接池中和保存最少空闲连接数
		dataSource.setMaxWaitMillis(1000*5); //设置连接池分配连接的最长时间，超出连接时间则抛出异常
		
		//连接数据库
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
		
	}
	
	public void dbcpFactoryTest() throws Exception {
		Properties pop  = new Properties();
		InputStream in = DBCPTest.class.getClassLoader().getResourceAsStream("jdbc1.properties");
		pop.load(in);
		
		//获取BasicDataSource对象
		DataSource dataSource= BasicDataSourceFactory.createDataSource(pop);
		Connection conn = dataSource.getConnection();
		System.out.println(conn);
		
	}

}
