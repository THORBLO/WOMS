package panel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OpAssign {
	
	public static void assignStatusMove(String assid) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "UPDATE assignment SET ass_status = ass_status + 1 WHERE ass_id = '" + assid + "'";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void packStatusMove(String packid) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (
				Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://127.0.0.1:3306/woms?useSSL=false&serverTimezone=UTC",
                        "root", "admin");
				Statement stmt = conn.createStatement();) 
		{
			String sql = "UPDATE package SET status = status + 1 WHERE package_id = '" + packid + "'";
			stmt.execute(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
