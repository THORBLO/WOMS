package algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class InsertAssignment {
	
	static boolean checkGateAlready (String date) {
		boolean judge = false;
		
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
			String sql = "SELECT * FROM gate_info WHERE g_date = '" + date +"'";
			ResultSet rd = stmt.executeQuery(sql);
			judge = rd.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return judge;
	}
	
	
	static void doInsert (String order, String commodity, int piece, int gate) {
		
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
			String sql = "INSERT INTO assignment(ass_order, ass_commodity, ass_piece, ass_gate, ass_status) VALUES"
					+ "('" + order + "', '" + commodity + "', " + piece + ", " + gate + ", 5)";
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
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("yyyy-MM-dd");
			Date date = new Date();
			
			Gson gson = new Gson();
			String stringList = gson.toJson(singleList);
			
			if (checkGateAlready(sdf.format(date))) {
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
					String sql = "UPDATE gate_info SET A" + i + " = '" + stringList + "' WHERE g_date = '" + sdf.format(date) + "'";
					stmt.execute(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
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
					String sql = "INSERT INTO gate_info(A" + i + ", g_date) VALUES('" + stringList + "', '" + sdf.format(date) + "')";
					System.out.println(sql);
					stmt.execute(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			for (int j = 0; j < singleList.size(); j++) {
				String order = singleList.get(j);
				int gate = i;
				addSingleAssignment(order, gate);
			}
		}
	}
	
	public static void addAssignmentIN (List<List<String>> list) {
		
		for (int i = 1; i <= list.size(); i++) {
			List<String> singleList = list.get(i-1);

			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern("yyyy-MM-dd");
			Date date = new Date();
			
			Gson gson = new Gson();
			String stringList = gson.toJson(singleList);
			
			if (checkGateAlready(sdf.format(date))) {
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
					String sql = "UPDATE gate_info SET B" + i + " = '" + stringList + "' WHERE g_date = '" + sdf.format(date) + "'";
					stmt.execute(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
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
					String sql = "INSERT INTO gate_info(B" + i + ", g_date) VALUES('" + stringList + "', '" + sdf.format(date) + "')";
					System.out.println(sql);
					stmt.execute(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			for (int j = 0; j < singleList.size(); j++) {
				String order = singleList.get(j);
				int gate = i;
				addSingleAssignmentIN(order, gate);
			}
		}
	}
	
	static void addSingleAssignmentIN (String order, int gate) {
		
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
			String sql = "SELECT rp_coid, rp_num FROM replenishment WHERE rp_id = '" + order + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			String commodity = rs.getString(1);
			
			int piece = rs.getInt(2);
			doInsertIN(order, commodity, piece, gate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	
	static void doInsertIN (String order, String commodity, int piece, int gate) {
		
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
			String sql = "INSERT INTO assignment(ass_order, ass_commodity, ass_piece, ass_gate, ass_status) VALUES"
					+ "('" + order + "', '" + commodity + "', " + piece + ", " + gate + ", 1)";
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
