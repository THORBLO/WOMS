package panel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.alee.laf.table.WebTable;

public class OrderDetail {
	
	public static WebTable createDetail(String order) {
		Vector<String> cloumn = new Vector<String>();
		cloumn.add("订单号");
		cloumn.add("产品编号");
		cloumn.add("订购数量");
		
		Vector<Vector<Object>> table = new Vector<Vector<Object>>();
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
			String sql = "SELECT * FROM oc_combined WHERE oc_order = '" + order + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getInt(3));
				
				table.add(row);
		
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		WebTable detailTable = new WebTable(table, cloumn);
		return detailTable;
		
	}
}
