package db_actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InsertAssignment {
	
	
	static void doInsert (String order, String commodity, int piece, int gate) {
		
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间 
        sdf.applyPattern("yyyy-MM-dd");  
        Date date = new Date();// 获取当前时间 
		
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
			String sql = "INSERT INTO assignment(ass_order, ass_commodity, ass_piece, ass_gate, ass_date) VALUES"
					+ "('" + order + "', '" + commodity + "', " + piece + ", " + gate + ", '" + sdf.format(date) + "')";
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	
	
	static void addSingleAssignment (String order, int gate) {
		
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
			String sql = "SELECT oc_commodity, oc_piece FROM oc_combined WHERE oc_order = '" + order + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String commodity = rs.getString(1);
				int piece = rs.getInt(2);
				doInsert(order, commodity, piece, gate);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
	public static void addAssignment (List<List<String>> list) {
		
		for (int i = 1; i <= list.size(); i++) {
			List<String> singleList = list.get(i-1);
			for (int j = 0; j < singleList.size(); j++) {
				String order = singleList.get(j);
				int gate = i;
				addSingleAssignment(order, gate);				
			}
		}
	}
	
}
