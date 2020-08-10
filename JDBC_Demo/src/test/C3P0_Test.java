package test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0_Test {
	@Test
	public void C3P0_Test() throws PropertyVetoException, SQLException {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/jdbcDemo");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("1234");
		Connection conn = comboPooledDataSource.getConnection();
		System.out.println(conn);
		
	}
	public void c3p0XmlTest() throws SQLException {
		DataSource dataSource = new ComboPooledDataSource("musql");
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}

}
