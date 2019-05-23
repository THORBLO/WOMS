package panel;

import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.table.WebTable;

public class createStaticTable {
	
	public static WebPanel assignTable() {
		WebPanel content = new WebPanel();
		
		Vector<String> cloumn = new Vector<String>();
		cloumn.add("任务ID");
		cloumn.add("订单号");
		cloumn.add("产品编号");
		cloumn.add("操作托盘数");
		cloumn.add("目标门区");
		cloumn.add("状态变更时间");
		cloumn.add("任务状态");
		
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
			String sql = "SELECT * FROM assignment_detail";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(5));
				row.add(rs.getString(6));
				row.add(rs.getString(7));
				table.add(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		WebTable pTable = new WebTable(table, cloumn);
		pTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(WebLabel.CENTER);
		pTable.setRowHeight(25);
		pTable.setDefaultRenderer(Object.class, dtcr);
		JTableHeader head = pTable.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(new Font("微软雅黑", Font.BOLD, 18));
		
		
		JScrollPane jScrollPane = new JScrollPane(pTable);
		content.add(jScrollPane);
		
		return content;
	}
	
	public static WebPanel packageTable() {
		WebPanel content = new WebPanel();
		
		Vector<String> cloumn = new Vector<String>();
		cloumn.add("托盘ID");
		cloumn.add("产品编号");
		cloumn.add("包装数量");
		cloumn.add("上一操作时间");
		cloumn.add("托盘状态");
		
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
			String sql = "SELECT * FROM package_detail";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Vector<Object> row = new Vector<Object>();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				row.add(rs.getString(6));
				table.add(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		WebTable pTable = new WebTable(table, cloumn);
		pTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(WebLabel.CENTER);
		pTable.setRowHeight(25);
		pTable.setDefaultRenderer(Object.class, dtcr);
		JTableHeader head = pTable.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(new Font("微软雅黑", Font.BOLD, 18));
		
		
		JScrollPane jScrollPane = new JScrollPane(pTable);
		content.add(jScrollPane);
		
		return content;
	}
	
	public static WebPanel storageTable() {
		WebPanel content = new WebPanel();
		
		Vector<String> cloumn = new Vector<String>();
		cloumn.add("行");
		cloumn.add("列");
		cloumn.add("高");
		cloumn.add("库位状况");
		
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
			String sql = "SELECT * FROM storage_detail";
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

		WebTable sTable = new WebTable(table, cloumn);
		sTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(WebLabel.CENTER);
		sTable.setRowHeight(25);
		sTable.setDefaultRenderer(Object.class, dtcr);
		JTableHeader head = sTable.getTableHeader();
		head.setPreferredSize(new Dimension(head.getWidth(), 35));
		head.setFont(new Font("微软雅黑", Font.BOLD, 18));
		
		
		JScrollPane jScrollPane = new JScrollPane(sTable);
		content.add(jScrollPane);
		
		return content;
	}

}
