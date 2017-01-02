package movies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleConnection {
	private String username = "ADAD16A";
	private String password = "adad16a";
	
	Connection connection = null;
	
	public OracleConnection() throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connection = DriverManager.getConnection(
			"jdbc:oracle:thin:@oraalu.fe.up.pt:1521:ALU",username,password);
	}
	
	public void closeConnection() throws SQLException {
		connection.close();
	}
	
	public ResultSet executeQuery(String query) throws SQLException {
		Statement stmt = connection.createStatement();
		return stmt.executeQuery(query);
	}
}