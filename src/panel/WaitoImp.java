package panel;

import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;

public class WaitoImp {
	
	public static WebTable supinviteTable (String supid) {
		Vector<String> cloumn = new Vector<String>();
		cloumn.add("要约ID");
		cloumn.add("产品编码");
		cloumn.add("需求数量");
		cloumn.add("要约日期");
		
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
			String sql = "SELECT invite_id, invite_coid, invite_num, invite_date FROM sup_invite WHERE invite_sup = '" + supid + "' AND invite_status = 1";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				table.add(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		WebTable supiTable = new WebTable(table, cloumn);
		supiTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(WebLabel.CENTER);
		supiTable.setRowHeight(25);
		supiTable.setDefaultRenderer(Object.class, dtcr);
		JTableHeader head = supiTable.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(new Font("微软雅黑", Font.BOLD, 18));
		return supiTable;
		
		
	}

	public static WebTable createImptable() {
		Vector<String> cloumn = new Vector<String>();
		cloumn.add("产品编号");
		cloumn.add("产品名");
		cloumn.add("供应商ID");
		cloumn.add("安全库存量");
		cloumn.add("在库存量");
		
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
			String sql = "SELECT * FROM woms.storage_inform";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				table.add(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DefaultTableModel model = new DefaultTableModel(table, cloumn);
		
		WebTable impTable = new WebTable(model);
		return impTable;
	}
	
}
